package Scenes;

import java.util.ArrayList;
import java.util.List;

import Cam.Camera;
import Entities.Entity;
import GUIs.GUITexture;
import Terrains.Terrain;
import Water.WaterTile;

public class Scene {
	private Camera cam;
	private List<Entity> entities=new ArrayList<Entity>();
	private List<Terrain> terrains=new ArrayList<Terrain>();
	private List<WaterTile> waters=new ArrayList<WaterTile>();
	private List<GUITexture> guis =new ArrayList<GUITexture>();
	
	public Scene()
	{}
	
	public Camera getCamera()
	{
		return cam;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}

	public List<Terrain> getTerrains() {
		return terrains;
	}

	public List<WaterTile> getWaters() {
		return waters;
	}

	public List<GUITexture> getGuis() {
		return guis;
	}

	public void AddEntity(Entity entity)
	{
		entities.add(entity);
	}
	
	public void AddTerrain(Terrain terrain)
	{
		terrains.add(terrain);
	}
	
	public void AddWater(WaterTile water)
	{
		waters.add(water);
	}
	
	public void AddGUI(GUITexture gui)
	{
		guis.add(gui);
	}
	
	public void Update()
	{
		for(Entity entity : entities)
		{
			//이곳에 업데이트 구무을 작성함.
		}
		
		for(WaterTile water : waters)
		{
			//업데이트 구문을 작성함.
		}
		
		for(GUITexture gui : guis)
		{
			gui.UpdateGUI();
		}
	}
	
	public void CleanUp()
	{
	}
	
}
