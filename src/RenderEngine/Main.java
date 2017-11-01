package RenderEngine;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Cam.Camera;
import Entities.Entity;
import GUIs.AlphaAnimator;
import GUIs.GUIAnimator;
import GUIs.GUITexture;
import Lights.PointLight;
import Models.RawModel;
import Models.TexturedModel;
import Renderer.MasterRenderer;
import Renderer.EntityRenderer;
import Shaders.StaticShader;
import Terrains.Terrain;
import sun.font.GlyphLayout.LayoutEngineFactory;

public class Main {
	
	public static void main(String[] args)
	{
		float timeValue=0.0f;
		DisplayManager.createDisplay();
		
		Loader loader=new Loader();
		MasterRenderer renderer=new MasterRenderer(loader);
		Camera MainCam=new Camera();
		MainCam.setPosition(new Vector3f(0.0f, 15.0f,40.0f));
		MainCam.pitch(-15.0f);
		PointLight light=new PointLight(new Vector3f(0.0f, 100f, 15.0f), new Vector3f(1.0f, 0f, 0f), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(1.0f, 1.0f, 1.0f));
	
		
		TexturedModel texModel=loader.loadTexturedModel("stall","stallTexture");
		Entity entity=new Entity(texModel);
		entity.setPosition(new Vector3f(0.0f, 0.0f, 0.0f));
		
		AlphaAnimator animator=new AlphaAnimator(0.04f);
		GUITexture gui=loader.loadGUI("play", "png", new Vector2f(0,0), new Vector2f(100,100),animator);
		gui.PlayAnimation();
		
		Terrain groundTerrain=new Terrain(0,0,loader,"terrain");
		
		
		while(!Display.isCloseRequested())
		{
			MainCam.Update();
			gui.UpdateGUI();
			renderer.processEntity(entity);
			renderer.processTerrain(groundTerrain);
			renderer.processGUI(gui);
			renderer.Render(light, MainCam);
			
			
			//screen swap
			DisplayManager.updateDisplay();
		}
		
		DisplayManager.closeDisplay();
	}

}
