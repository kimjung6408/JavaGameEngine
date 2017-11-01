package Lights;

import org.lwjgl.util.vector.Vector3f;

public class DirectionalLight extends Light {
	Vector3f direction;
	
	public DirectionalLight(Vector3f position, Vector3f direction, Vector3f diffuseColor, Vector3f specularColor)
	{
		super(LightType.DIRECTIONAL_LIGHT, position, diffuseColor, specularColor);
		this.direction=direction;
	}
	
	public Vector3f getDirection()
	{
		return this.direction;
	}
	
	public void setDirection(Vector3f direction)
	{
		this.direction=direction;
	}
}
