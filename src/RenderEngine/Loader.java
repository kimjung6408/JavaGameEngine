package RenderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.lwjgl.util.vector.*;

import GUIs.GUIAnimator;
import GUIs.GUITexture;
import Models.RawModel;
import Models.TexturedModel;
import Textures.ModelTexture;

public class Loader {
	
	private List<Integer> vaos=new ArrayList<Integer>();
	private List<Integer> vbos=new ArrayList<Integer>();
	private List<Integer> textures=new ArrayList<Integer>();
	
	public RawModel loadToVAO(float[] positions, float[] texCoords, float[] normals, int[] indices)
	{
		int vaoID=createVAO();
		
		bindIndicesBuffer(indices);
		
		// attribute Index , dimension, dataBuf
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1,2, texCoords);
		storeDataInAttributeList(2,3, normals);
		unbindVAO();
		
		return new RawModel(vaoID, indices.length);
	}
	
	public RawModel loadToVAO(float[] positions, int dimension)
	{
		int vaoID=createVAO();
	
		
		// attribute Index , dimension, dataBuf
		storeDataInAttributeList(0, dimension, positions);
		unbindVAO();
		
		return new RawModel(vaoID, 6);
	}
	
	public int loadTexture(String fileName)
	{
		Texture texture=null;
		try {
			texture=TextureLoader.getTexture("PNG", new FileInputStream("res/image/"+fileName+".png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int texID=texture.getTextureID();
		
		return texID;
		
	}
	
	public int loadTexture(String fileName, String filetype)
	{
		Texture texture=null;
		try {
			texture=TextureLoader.getTexture(filetype, new FileInputStream("res/image/"+fileName+"."+filetype));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int texID=texture.getTextureID();
		
		return texID;
		
	}
	
	void CleanUp()
	{
		for(int vao : vaos)
		{
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo : vbos)
		{
			GL15.glDeleteBuffers(vbo);
		}
	}
	
	private int createVAO()
	{
		int vaoID=GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		return vaoID;
	}
	
	private void storeDataInAttributeList(int attributeNumber, int dimension, float[] data)
	{
		int vboID=GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer=storeDataInFloatBuffer(data);
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, dimension, GL11.GL_FLOAT	, false, 0,0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	
	}
	
	private void unbindVAO()
	{
		GL30.glBindVertexArray(0);
	}
	
	private void bindIndicesBuffer(int[] indices)
	{
		int vboID=GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
	
		IntBuffer buffer=storeDataInIntBuffer(indices);
		
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	
		
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data)
	{
		IntBuffer buffer=BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
		
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data)
	{
		FloatBuffer buffer=BufferUtils.createFloatBuffer(data.length);
		
		buffer.put(data);
		buffer.flip(); //버퍼를 쓰기모드에서 읽기모드로 전환
		return buffer;
	}

	public TexturedModel loadTexturedModel(String ObjFile ,String texFile)
	{
		RawModel raw=OBJLoader.loadObjModel(ObjFile, this);
		ModelTexture texture=new ModelTexture(loadTexture(texFile));
		
		TexturedModel model=new TexturedModel(raw, texture);
		
		return model;
	}
	
	public GUITexture loadGUI(String GUI_File, Vector2f win_pos, Vector2f scale)
	{
		 int textureID=loadTexture(GUI_File);
		
		 GUITexture texture=new GUITexture(textureID, win_pos, 	scale);
		 
		 return texture;
	}
	
	public GUITexture loadGUI(String GUI_File, String type, Vector2f left_top, Vector2f right_bottom)
	{
		int textureID=loadTexture(GUI_File, type);
		Vector2f position=new Vector2f((left_top.x+right_bottom.x)/2f,(left_top.y+right_bottom.y)/2f);
		
		float x_scale=Math.abs((right_bottom.x-left_top.x))/(float)DisplayManager.WIDTH;
		float y_scale=Math.abs((right_bottom.y-left_top.y))/(float)DisplayManager.HEIGHT;
		
		Vector2f scale=new Vector2f(x_scale, y_scale);
		
		GUITexture gui=new GUITexture(textureID, position, scale);
		
		return gui;
	}
	
	public GUITexture loadGUI(String GUI_File, String type, Vector2f left_top, Vector2f right_bottom, GUIAnimator animator)
	{
		int textureID=loadTexture(GUI_File, type);
		Vector2f position=new Vector2f((left_top.x+right_bottom.x)/2f,(left_top.y+right_bottom.y)/2f);
		
		float x_scale=Math.abs((right_bottom.x-left_top.x))/(float)DisplayManager.WIDTH;
		float y_scale=Math.abs((right_bottom.y-left_top.y))/(float)DisplayManager.HEIGHT;
		
		Vector2f scale=new Vector2f(x_scale, y_scale);
		
		GUITexture gui=new GUITexture(textureID, position, scale, animator);
		
		return gui;
	}
}
