package Renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import Cam.Camera;

import java.util.List;

import Entities.Entity;
import GUIs.GUIRenderer;
import GUIs.GUIShader;
import GUIs.GUITexture;
import Lights.PointLight;
import Models.TexturedModel;
import RenderEngine.Loader;
import Shaders.StaticShader;
import Shaders.TerrainShader;
import Terrains.Terrain;

public class MasterRenderer {
	
	private StaticShader entityShader;
	private TerrainShader terrainShader;
	private EntityRenderer entityRenderer;
	private TerrainRenderer terrainRenderer;
	
	private GUIShader guiShader;
	private GUIRenderer guiRenderer;
	private Map<TexturedModel, List<Entity>> entities=new HashMap<TexturedModel, List<Entity>>();
	private List<GUITexture> guis=new ArrayList<GUITexture>();
	private List<Terrain> terrains=new ArrayList<Terrain>();
	public MasterRenderer(Loader loader)
	{
		entityShader=new StaticShader();
		entityRenderer=new EntityRenderer(entityShader);
		guiShader=new GUIShader();
		guiRenderer=new GUIRenderer(loader);
		
		terrainShader=new TerrainShader();
		terrainRenderer=new TerrainRenderer(terrainShader);
	}
	
	public static void enableCulling()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void prepare()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
	}
	
	
	public void Render(PointLight sun, Camera cam)
	{
		prepare();
		
		//render entity
		entityShader.start();
		
		entityShader.loadLight(sun);
		entityShader.loadViewMatrix(cam);
		entityShader.loadProjMatrix(cam.getProjMatrix());
		
		entityRenderer.Render(entities);
		entityShader.stop();
		
		//render terrain
		terrainShader.start();
		terrainShader.loadViewMatrix(cam);
		terrainShader.loadProjMatrix(cam.getProjMatrix());
		terrainShader.loadLight(sun);
		
		terrainRenderer.Render(terrains);
		terrainShader.stop();
		
		//render gui
		guiRenderer.render(guis);
		
		
		terrains.clear();
		guis.clear();
		entities.clear();
	}
	
	public void processEntity(Entity entity)
	{
		TexturedModel entityModel=entity.getModel();
		
		List<Entity> batch=entities.get(entityModel);
		
		if(batch!=null){ batch.add(entity);}
		else
		{
			List<Entity> entityList=new ArrayList<Entity>();
			entityList.add(entity);
			entities.put(entityModel, entityList);
		}
	}
	
	public void processTerrain(Terrain terrain)
	{
		terrains.add(terrain);
	}
	
	public void processGUI(GUITexture gui)
	{
		guis.add(gui);
	}
	
	public void CleanUp()
	{
		entityShader.CleanUp();
	}

}
