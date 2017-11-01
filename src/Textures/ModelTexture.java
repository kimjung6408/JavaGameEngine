package Textures;

import Entities.Material;

public class ModelTexture {
	
	private int textureID;
	private boolean hasTransparency;
	Material material;
	
	public ModelTexture(int textureID)
	{
		this.textureID=textureID;
		material=new Material(20.0f, 0.2f);
		this.hasTransparency=false;
	}
	
	public ModelTexture(int textureID, boolean hasTransparency)
	{
		this.textureID=textureID;
		material=new Material(20.0f, 0.2f);
		this.hasTransparency=hasTransparency;
	}
	
	public int getID()
	{
		return textureID;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public boolean isHasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}
	
	
	
}
