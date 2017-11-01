package Renderer;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import Cam.Camera;
import Entities.Entity;
import Lights.PointLight;
import Models.RawModel;
import Models.TexturedModel;
import Shaders.StaticShader;
import Textures.ModelTexture;
import ToolBox.Maths;

public class EntityRenderer {
	
	private StaticShader shader;
	
	public EntityRenderer(StaticShader shader)
	{
		MasterRenderer.enableCulling();
		this.shader=shader;
	}

	
	public void Render(Map<TexturedModel, List<Entity>> entities)
	{
		
		for(TexturedModel model : entities.keySet())
		{
			prepareTexturedModel(model);
			
			List<Entity> batch=entities.get(model);
			
			for(Entity entity : batch)
			{
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT,0);
			}
			
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model)
	{
		RawModel raw=model.getRawModel();
		
		GL30.glBindVertexArray(raw.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		if(model.getTexture().isHasTransparency())
		{
			MasterRenderer.disableCulling();
		}
		
		ModelTexture texture=model.getTexture();
		shader.loadMaterial(texture.getMaterial());
		
		//activate texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	private void prepareInstance(Entity entity)
	{
		//load matrices
		Matrix4f WorldMat=Maths.createWorldMatrix(entity.getPosition(), entity.getRotation(), entity.getScale());
		
		shader.loadWorldMatrix(WorldMat);
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
