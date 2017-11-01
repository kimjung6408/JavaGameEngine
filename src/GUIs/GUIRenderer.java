package GUIs;

import java.util.List;
import java.util.Vector;

import Models.*;
import RenderEngine.Loader;
import ToolBox.Maths;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector2f;


//triangle strip을 이용해 GUI를 렌더링한다.
public class GUIRenderer {
	private final RawModel quad;
	private GUIShader shader;
	private Vector2f resolution;
	
	public GUIRenderer(Loader loader)
	{
		float[] positions={-1,1, -1,-1, 1,1, 1,-1};
		quad=loader.loadToVAO(positions,2);
		shader=new GUIShader();
		resolution=new Vector2f((float)Display.getWidth(), (float)Display.getHeight());
	}
	
	public void render(List<GUITexture> guis)
	{
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		
		//set enable alpha blending
		enableBlending();
		
		//disable depth test. because intersected gui rendering must be able.
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		//render
		for(GUITexture gui : guis)
		{
			//load Texture
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTextureID());
			
			//load transformation matrix
			Matrix4f transformationMatrix=Maths.createWorldMatrix(new Vector2f(0,0), gui.getRotation(), gui.getScale());
			shader.loadWorldMatrix(transformationMatrix);
			shader.loadResolution(new Vector2f(resolution));
			shader.loadGuiPosition(gui.getPosition());
			shader.loadAlpha(gui.getAlpha());
			
			//draw gui with triangle strip
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		
		
		//end
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		disableBlending();
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	private void enableBlending()
	{
		//set enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	private void disableBlending()
	{
		//set enable alpha blending
		GL11.glDisable(GL11.GL_BLEND);
	}
	
	
	public void cleanUp()
	{
		shader.CleanUp();
	}
}
