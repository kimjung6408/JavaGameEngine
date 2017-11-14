package RenderEngine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Cam.Camera;
import Entities.Entity;
import GUIs.AlphaAnimator;
import GUIs.GUIAnimator;
import GUIs.GUITexture;
import Lights.Light;
import Lights.PointLight;
import Models.RawModel;
import Models.TexturedModel;
import Renderer.MasterRenderer;
import Renderer.EntityRenderer;
import Shaders.StaticShader;
import Terrains.Terrain;
import Water.WaterFrameBuffers;
import Water.WaterRenderer;
import Water.WaterTile;
import sun.font.GlyphLayout.LayoutEngineFactory;

public class Main {
	
	public static void main(String[] args)
	{
		float timeValue=0.0f;
		DisplayManager.createDisplay();
		//loader
		Loader loader=new Loader();
		//renderer
		MasterRenderer renderer=new MasterRenderer(loader);
		//camera
		Camera MainCam=new Camera();
		MainCam.setPosition(new Vector3f(230.0f, 140.0f,240.0f));
		MainCam.pitch(-30.0f);
		
		//light
		PointLight sunlight=new PointLight(new Vector3f(0.0f, 5000f, 15.0f), new Vector3f(1.0f, 0f, 0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f));
	
		
		TexturedModel texModel=loader.loadTexturedModel("stall","stallTexture");
		Entity objStall=new Entity(texModel);
		objStall.setPosition(new Vector3f(0.0f, 0.0f, 0.0f));
		objStall.setScale(5.0f);
		
		AlphaAnimator animator=new AlphaAnimator(0.04f);
		GUITexture gui=loader.loadGUI("play", "png", new Vector2f(0,0), new Vector2f(100,100),animator);
		gui.PlayAnimation();
		
		Terrain groundTerrain=new Terrain(0,0,loader,"terrain");
		
		//Objects for rendering water
		WaterRenderer waterRenderer=new WaterRenderer(loader);
		List<WaterTile> waters=new ArrayList<WaterTile>();
		WaterFrameBuffers waterFBOs=new WaterFrameBuffers();
		
		WaterTile water=new WaterTile(220, 120, 2.0f);
		waters.add(water);
		
		
		List<GUITexture> guis=new ArrayList<GUITexture>();
		List<Entity> entities=new ArrayList<Entity>();
		List<Terrain> terrains=new ArrayList<Terrain>();
		List<Light> lights=new ArrayList<Light>();
		
		guis.add(gui);
		entities.add(objStall);
		terrains.add(groundTerrain);
		lights.add(sunlight);
		
		while(!Display.isCloseRequested())
		{
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			//Render to frame buffer
			//Render to Reflection Texture
			waterFBOs.BindReflectFBO();
			float distance=2*(MainCam.getPosition().y)-water.getHeight();
			MainCam.getPosition().y-=distance;
			MainCam.InvertPitch();
			renderer.RenderScene(entities, terrains, lights, MainCam, new Vector4f(0,1,0,-water.getHeight()+1f));
			MainCam.getPosition().y+=distance;
			MainCam.InvertPitch();
			
			//Render to Refraction Texture
			waterFBOs.BindRefractFBO();
			renderer.RenderScene(entities, terrains, lights, MainCam, new Vector4f(0,-1,0, water.getHeight()));
			
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			waterFBOs.UnbindCurrentFBO();
			
			MainCam.Update();
			gui.UpdateGUI();
			
			renderer.processEntity(objStall);
			renderer.processTerrain(groundTerrain);
			renderer.processGUI(gui);
			renderer.Render(sunlight, MainCam, new Vector4f(0,-1,0,100000));
			
			waterRenderer.Render(waters, MainCam, waterFBOs, sunlight);
			
			
			//screen swap
			DisplayManager.updateDisplay();
		}
		
		DisplayManager.closeDisplay();
	}

}
