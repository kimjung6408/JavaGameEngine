package Water;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Lights.Light;
import Shaders.ShaderProgram;

public class WaterShader extends ShaderProgram {
	private static final String VERTEX_FILEPATH="src/Water/waterVertex.glsl";
	private static final String FRAGMENT_FILEPATH="src/Water/waterFragment.glsl";
	
	private int loc_worldMat;
	private int loc_viewMat;
	private int loc_projMat;
	private int loc_ReflectTexture;
	private int loc_RefractTexture;
	private int loc_DUDVMap;
	private int loc_NormalMap;
	private int loc_DepthMap;
	
	private int loc_moveFactor;
	private int loc_cameraPosition;
	
	private int loc_lightColor;
	private int loc_lightPosition;
	
	public WaterShader()
	{
		super(VERTEX_FILEPATH,FRAGMENT_FILEPATH);		
	}
	
	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		BindAttribute(0, "position");
	}
	
	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		loc_projMat=getUniformLocation("ProjMat");
		loc_viewMat=getUniformLocation("ViewMat");
		loc_worldMat=getUniformLocation("WorldMat");
		loc_RefractTexture=getUniformLocation("RefractTexture");
		loc_ReflectTexture=getUniformLocation("ReflectTexture");
		loc_DUDVMap=getUniformLocation("DUDVMap");
		loc_NormalMap=getUniformLocation("NormalMap");
		loc_DepthMap=getUniformLocation("DepthMap");
		
		loc_moveFactor=getUniformLocation("moveFactor");
		loc_cameraPosition=getUniformLocation("cameraPosition");
		loc_lightColor=getUniformLocation("lightColor");
		loc_lightPosition=getUniformLocation("lightPosition");
	}
	
	public void connectTextureUnits()
	{
		super.loadInt(loc_ReflectTexture, 0);
		super.loadInt(loc_RefractTexture, 1);
		super.loadInt(loc_DUDVMap, 2);
		super.loadInt(loc_NormalMap, 3);
		super.loadInt(loc_DepthMap, 4);
		
	}
	
	public void loadProjMat(Matrix4f projMat)
	{
		loadMatrix(loc_projMat, projMat);
	}
	
	public void loadViewMat(Matrix4f viewMat)
	{
		loadMatrix(loc_viewMat, viewMat);
	}
	
	public void loadCameraPosition(Vector3f cameraPosition)
	{
		loadVector3(loc_cameraPosition, cameraPosition);
	}
	
	public void loadWorldMat(Matrix4f worldMat)
	{
		loadMatrix(loc_worldMat, worldMat);
	}
	
	public void loadMoveFactor(float moveFactor)
	{
		loadFloat(loc_moveFactor, moveFactor);
	}
	
	public void loadLight(Light light)
	{
		loadVector3(loc_lightColor, light.getSpecularColor());
		loadVector3(loc_lightPosition, light.getPosition());
	}
}
