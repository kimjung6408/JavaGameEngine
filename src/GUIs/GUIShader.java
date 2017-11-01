package GUIs;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import Shaders.ShaderProgram;

public class GUIShader extends ShaderProgram{
    
    private static final String VERTEX_FILE = "src/guis/guiVertexShader.glsl";
    private static final String FRAGMENT_FILE = "src/guis/guiFragmentShader.glsl";
     
    private int loc_WorldMat;
    private int loc_Resolution;
    private int loc_WindowPos;
    private int loc_Alpha;
 
    public GUIShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadWorldMatrix(Matrix4f matrix){
        super.loadMatrix(loc_WorldMat, matrix);
    }
    
    public void loadResolution(Vector2f resolution)
    {
    	super.loadVector2f(loc_Resolution	, resolution);
    }
    
    public void loadGuiPosition(Vector2f position)
    {
    	super.loadVector2f(loc_WindowPos, position);
    }
    
    public void loadAlpha(float Alpha)
    {
    	super.loadFloat(loc_Alpha, Alpha);
    }
 
    @Override
    protected void getAllUniformLocations() {
        loc_WorldMat= super.getUniformLocation("WorldMat");
        loc_Resolution=super.getUniformLocation("Resolution");
        loc_WindowPos=super.getUniformLocation("WindowPos");
        loc_Alpha=super.getUniformLocation("Alpha");
    }
 
    @Override
    protected void bindAttributes() {
        super.BindAttribute(0, "position");
    }
     
     
     
 
}
