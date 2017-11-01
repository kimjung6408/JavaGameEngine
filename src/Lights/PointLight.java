package Lights;

import org.lwjgl.util.vector.Vector3f;

public class PointLight extends Light{
	Vector3f attenuation;
	
	public PointLight(Vector3f position ,Vector3f attenuation, Vector3f diffuseColor, Vector3f specularColor)
	{
		super(LightType.POINT_LIGHT, position, diffuseColor, specularColor);
		this.attenuation=attenuation;
	}

	public Vector3f getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(float constant, float linear, float square) {
		this.attenuation = new Vector3f(constant, linear, square);
	}
	
	
}
