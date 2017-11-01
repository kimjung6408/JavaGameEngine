package Terrains;

import Models.RawModel;
import RenderEngine.Loader;
import Textures.ModelTexture;

public class Terrain {
	private static final float SIZE=800;
	private static final int VERTEX_COUNT=128;
	
	private float x;
	private float z;
	private RawModel model;
	private ModelTexture texture;
	private float textureDensity;
	
	public Terrain(int gridX, int gridZ, Loader loader, String TextureFileName)
	{
		this.texture=new ModelTexture(loader.loadTexture(TextureFileName));
		this.x=gridX*SIZE;
		this.z=gridZ*SIZE;
		this.model=generateTerrain(loader);
		this.textureDensity=32.0f;
	}
	
	private RawModel generateTerrain(Loader loader){
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int dz=0;dz<VERTEX_COUNT;dz++){
			for(int dx=0;dx<VERTEX_COUNT;dx++){
				vertices[vertexPointer*3] = (float)dx/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = 0;
				vertices[vertexPointer*3+2] = (float)dz/((float)VERTEX_COUNT - 1) * SIZE;
				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)dx/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)dz/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}

	public RawModel getModel() {
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public float getTextureDensity() {
		return textureDensity;
	}

	public void setTextureDensity(float textureDensity) {
		this.textureDensity = textureDensity;
	}
	
	
}
