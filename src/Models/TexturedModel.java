package Models;

import RenderEngine.Loader;
import Textures.ModelTexture;

public class TexturedModel {
	private RawModel rawModel;
	private ModelTexture texture;
	
	public TexturedModel(Loader loader, float[] pos, float[] texCoords, float[] normals, int[] indices,String texFile)
	{
		this.rawModel=loader.loadToVAO(pos, texCoords, normals, indices);
		this.texture=new ModelTexture(loader.loadTexture(texFile));
	}
	
	public TexturedModel(RawModel model , ModelTexture texture)
	{
		this.rawModel=model;
		this.texture=texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	
}
