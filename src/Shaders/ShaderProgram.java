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
	
	//vertex shader, fragment shader�� �������ϰ�,
	//�ش� shader���� ���������� ���� shader program�� �����Ѵ�.
	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		vertexShaderID=loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID=loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		
		//���ο� shader program�� �����Ѵ�.
		//shader program�� ������ shader stage�� �����Ѵ�.
		programID=GL20.glCreateProgram();
		
		//vertex shader ���������� �����Ѵ�.
		GL20.glAttachShader(programID, vertexShaderID);
		
		//fragment shader ���������� �����Ѵ�.
		GL20.glAttachShader(programID, fragmentShaderID);
		
		//��ũ�Ѵ�.
		GL20.glLinkProgram(programID);
		
		//��� ������ ���̴� �������� ���α׷����� ����Ѵ�.
		GL20.glValidateProgram(programID);
		
		//attribute���� ����Ѵ�.
		bindAttributes();
		getAllUniformLocations();
		
	}
	
	protected abstract void bindAttributes();
	protected abstract void getAllUniformLocations();
	
	protected int getUniformLocation(String uniformName)
	{
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	//���̴� ���α׷��� ���������� �����Ѵ�.
	public void start()
	{
		GL20.glUseProgram(programID);
		
	}
	
	//���̴� ���α׷��� �������� ������ ������.
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
	
	//�ش� VAO�� attribute ��ȣ�� ���̴� �������� �󿡼� �̸��� variableName�� ������ �����Ѵ�.
	protected void BindAttribute(int attributeIdx, String variableName)
	{
		GL20.glBindAttribLocation(programID, attributeIdx, variableName);
	}
	
	private static int loadShader(String file, int type)
	{
		StringBuilder shaderSource=new StringBuilder();
		
		//������ �� line�� �о shaderSource�� �����Ѵ�.
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
		
		//shader�� �����Ѵ�.
		int ShaderID=GL20.glCreateShader(type);
		//shader �ҽ��ڵ带 �����Ѵ�.
		GL20.glShaderSource(ShaderID, shaderSource);
		//shader�� �������Ѵ�.
		GL20.glCompileShader(ShaderID);
		
		//Compile Status�� üũ�ϰ�,
		//�����ϰ�� -1�� �����Ѵ�.
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
