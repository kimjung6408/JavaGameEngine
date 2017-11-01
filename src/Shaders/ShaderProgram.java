package Shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public abstract class ShaderProgram {

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	private static FloatBuffer matrixBuffer=BufferUtils.createFloatBuffer(16);
	
	//vertex shader, fragment shader를 컴파일하고,
	//해당 shader들을 스테이지로 갖는 shader program을 생성한다.
	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		vertexShaderID=loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID=loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		
		//새로운 shader program을 생성한다.
		//shader program은 렌더링 shader stage를 설정한다.
		programID=GL20.glCreateProgram();
		
		//vertex shader 스테이지를 설정한다.
		GL20.glAttachShader(programID, vertexShaderID);
		
		//fragment shader 스테이지를 설정한다.
		GL20.glAttachShader(programID, fragmentShaderID);
		
		//링크한다.
		GL20.glLinkProgram(programID);
		
		//사용 가능한 쉐이더 스테이지 프로그램으로 등록한다.
		GL20.glValidateProgram(programID);
		
		//attribute들을 등록한다.
		bindAttributes();
		getAllUniformLocations();
		
	}
	
	protected abstract void bindAttributes();
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName)
	{
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	//쉐이더 프로그램의 스테이지를 적용한다.
	public void start()
	{
		GL20.glUseProgram(programID);
		
	}
	
	//쉐이더 프로그램의 스테이지 적용을 끝낸다.
	public void stop()
	{
		GL20.glUseProgram(0);
	}
	
	public void CleanUp()
	{
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	//해당 VAO의 attribute 번호에 쉐이더 스테이지 상에서 이름이 variableName인 변수를 연결한다.
	protected void BindAttribute(int attributeIdx, String variableName)
	{
		GL20.glBindAttribLocation(programID, attributeIdx, variableName);
	}
	
	private static int loadShader(String file, int type)
	{
		StringBuilder shaderSource=new StringBuilder();
		
		//파일의 각 line을 읽어서 shaderSource에 저장한다.
		try
		{
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String line;
			while((line=reader.readLine())!=null)
			{
				shaderSource.append(line).append("\n");
			}
			
		}
		catch(IOException e)
		{
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		
		//shader를 생성한다.
		int ShaderID=GL20.glCreateShader(type);
		//shader 소스코드를 설정한다.
		GL20.glShaderSource(ShaderID, shaderSource);
		//shader를 컴파일한다.
		GL20.glCompileShader(ShaderID);
		
		//Compile Status를 체크하고,
		//실패일경우 -1을 리턴한다.
		if(GL20.glGetShaderi(ShaderID, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE	)
		{
				System.out.println(GL20.glGetShaderInfoLog(ShaderID, 1024));
				System.err.println("Could not compile shader");
				
				System.exit(-1);
		}
		
		return ShaderID;
	}

	protected void loadFloat(int location, float value)
	{
		GL20.glUniform1f(location, value);
	}
	
	protected void loadVector3(int location, Vector3f vector)
	{
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	protected void loadVector4(int location, Vector4f vector)
	{
		GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
	}
	
	protected void loadVector2f(int loc, Vector2f vec)
	{
		GL20.glUniform2f(loc, vec.x, vec.y);
	}
	
	protected void loadBoolean(int location, boolean value)
	{
		float toLoad=0;
		
		if(value)
		{
			toLoad=1;
		}
		
		GL20.glUniform1f(location, toLoad);
	}

	protected void loadMatrix(int location, Matrix4f matrix)
	{
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
}
