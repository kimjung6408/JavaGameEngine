package Shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Cam.Camera;
import Entities.Material;
import Lights.PointLight;
import ToolBox.Maths;

public class TerrainShader extends ShaderProgram {
	private static final String VERTEX_FILE="src/Shaders/terrainVertexShader.glsl";
	private static final String FRAGMENT_FILE="src/Shaders/terrainFragmentShader.glsl";
	
	private int loc_WorldMat;
	private int loc_ViewMat;
	private int loc_ProjMat;
	
	private int loc_lightPos;
	private int loc_lightDiffuseColor;
	private int loc_lightSpecularColor;
	private int loc_lightAttenuation;
	private int loc_shineDamper;
	private int loc_reflectivity;
	private int loc_cameraPos;
	private int loc_texDensity;
	
	public TerrainShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.BindAttribute(0, "position");
		super.BindAttribute(1, "texCoords");
		super.BindAttribute(2,  "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		loc_WorldMat=super.getUniformLocation("WorldMat");
		loc_ViewMat=super.getUniformLocation("ViewMat");
		loc_ProjMat=super.getUniformLocation("ProjMat");
		loc_lightPos=super.getUniformLocation("lightPos");
		loc_lightSpecularColor=super.getUniformLocation("lightSpecularColor");
		loc_lightDiffuseColor=super.getUniformLocation("lightDiffuseColor");
		loc_lightAttenuation=super.getUniformLocation("lightAttenuation");
		loc_shineDamper=super.getUniformLocation("shineDamper");
		loc_reflectivity=super.getUniformLocation("reflectivity");
		loc_cameraPos=super.getUniformLocation("cameraPos");
		loc_texDensity=super.getUniformLocation("texDensity");
		
	}
	
	public void loadTextureDensity(float texDensity)
	{
		super.loadFloat(loc_texDensity, texDensity);
	}
	
	public void loadWorldMatrix(Matrix4f matrix)
	{
		super.loadMatrix(loc_WorldMat, matrix);
	}
	
	public void loadViewMatrix(Camera camera)
	{
		Matrix4f matrix=Maths.createViewMatrix(camera);
		super.loadMatrix(loc_ViewMat, matrix);
	}

	public void loadLight(PointLight light)
	{
		super.loadVector3(loc_lightPos, light.getPosition());
		super.loadVector3(loc_lightAttenuation, light.getAttenuation());
		super.loadVector3(loc_lightDiffuseColor, light.getDiffuseColor());
		super.loadVector3(loc_lightSpecularColor, light.getSpecularColor());
		
	}
	
	public void loadMaterial(Material material)
	{
		super.loadFloat(loc_shineDamper, material.getShineDamper());
		super.loadFloat(loc_reflectivity, material.getReflectivity());
	}
	
	public void loadCameraPosition(Vector3f camPos)
	{
		super.loadVector3(loc_cameraPos, camPos);
	}
	
	public void loadProjMatrix(Matrix4f matrix)
	{
		super.loadMatrix(loc_ProjMat, matrix);
	}
}
