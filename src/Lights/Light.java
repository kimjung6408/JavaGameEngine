package Lights;

import org.lwjgl.util.vector.Vector3f;

public abstract class Light {
	public enum LightType{
		DIRECTIONAL_LIGHT,
		SPOT_LIGHT,
		POINT_LIGHT,
	};
	
	private final LightType type;
	private Vector3f position;
	private Vector3f diffuseColor;
	private Vector3f specularColor;
	
	public Light(LightType type, Vector3f position, Vector3f diffuseColor, Vector3f specularColor)
	{
		this.type=type;
		this.position=position;
		this.diffuseColor=diffuseColor;
		this.specularColor=specularColor;
	}
	
	public void setPosition(Vector3f position)
	{
		this.position=position;
	}
	
	public void Translate(float dx, float dy, float dz)
	{
		position.x+=dx;
		position.y+=dy;
		position.z+=dz;
	}
	
	public Vector3f getPosition()
	{
		return position;		
	}
	
	public Vector3f getDiffuseColor()
	{
		return diffuseColor;
	}
	
	public Vector3f getSpecularColor()
	{
		return specularColor;
	}
	
	public LightType getType()
	{
		return type;
	}
	
	public void setDiffuseColor(Vector3f color)
	{
		this.diffuseColor=color;
	}
	
	public void setSpecularColor(Vector3f color)
	{
		this.specularColor=color;
	}
	
	
}
