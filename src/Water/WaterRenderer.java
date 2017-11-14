package Water;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Cam.Camera;
import Lights.Light;
import Models.RawModel;
import RenderEngine.DisplayManager;
import RenderEngine.Loader;
import ToolBox.Maths;

public class WaterRenderer {
	private RawModel quad;
	private WaterShader shader;
	private static final String DUDV_MAP="dudv";
	private static final String NORMAL_MAP="waterNormalMap";
	private static final float WAVE_SPEED=0.13f;
	
	private int dudvTexture;
	private int normalMapTexture;
	private float moveFactor;
	public WaterRenderer(Loader loader)
	{
		this.shader=new WaterShader();
		SetupVAO(loader);
		dudvTexture=loader.loadTexture(DUDV_MAP);
		normalMapTexture=loader.loadTexture(NORMAL_MAP);
		
		moveFactor=0.0f;
		
	}
	
	public void Render(List<WaterTile> waters, Camera camera, WaterFrameBuffers fbos, Light sun)
	{
		PrepareRendering(camera, fbos);
		shader.loadLight(sun);
		
		for(WaterTile water : waters)
		{
			//Load world matrix
			Matrix4f WorldMat=Maths.createWorldMatrix(
					new Vector3f(water.getX(), water.getHeight(), water.getZ()) //position
					, new Vector3f(0,0,0), //rotation
					water.TILE_SIZE);// size
			shader.loadWorldMat(WorldMat);
			shader.connectTextureUnits();
			
			
			
			
			
			
			
			
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, quad.getVertexCount());
			
		}
		
		
		FinishRendering();
	}
	
	private void PrepareRendering(Camera camera, WaterFrameBuffers fbos)
	{
		//update move factor
		moveFactor+=WAVE_SPEED*DisplayManager.DeltaTime();
		moveFactor%=1;
		
		
		shader.start();
		shader.loadViewMat(Maths.createViewMatrix(camera));
		shader.loadProjMat(camera.getProjMatrix());
		shader.loadMoveFactor(moveFactor);
		shader.loadCameraPosition(camera.getPosition());
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		
		//activate textures
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getReflectTexture());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractTexture());
	
		//load dudv map
		GL13.glActiveTexture(GL13.GL_TEXTURE2);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, dudvTexture);
		
		//load normal map
		GL13.glActiveTexture(GL13.GL_TEXTURE3);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, normalMapTexture);
		
		//load depth map
		GL13.glActiveTexture(GL13.GL_TEXTURE4);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fbos.getRefractDepthTexture());
	}
	
	private void FinishRendering()
	{
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	private void SetupVAO(Loader loader)
	{
		float[] vertices={-1,-1,-1,1,1,-1,1,-1,-1,1,1,1};
		quad=loader.loadToVAO(vertices, 2);
	}
}
