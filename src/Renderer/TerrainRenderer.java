package Renderer;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Entities.Entity;
import Models.RawModel;
import Models.TexturedModel;
import Shaders.TerrainShader;
import Terrains.Terrain;
import Textures.ModelTexture;
import ToolBox.Maths;

public class TerrainRenderer {
	
	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader  shader)
	{
		this.shader=shader;
	}
	
	public void Render(List<Terrain> terrains)
	{
		for(Terrain terrain : terrains)
		{
			prepareTerrain(terrain);
			
		GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT,0);
			
			unbindTexturedModel();
		}
	}
	
	private void prepareTerrain(Terrain terrain)
	{
		RawModel raw=terrain.getModel();
		
		GL30.glBindVertexArray(raw.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	
		
		ModelTexture texture=terrain.getTexture();
		shader.loadMaterial(texture.getMaterial());
		
		//load matrices
		Matrix4f WorldMat=Maths.createWorldMatrix(new Vector3f(terrain.getX(),0, terrain.getZ()), new Vector3f(0,0,0), 1);
		
		shader.loadWorldMatrix(WorldMat);
		shader.loadTextureDensity(terrain.getTextureDensity());
		
		//activate texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void unbindTexturedModel()
	{
		MasterRenderer.enableCulling();
		//렌더링 완료 후 attribute 비활성화.
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		//unbind VAO
		GL30.glBindVertexArray(0);
	}
}
