package Entities;

public class Material {
	private float shineDamper;
	private float reflectivity;
	
	public Material(float shineDamper, float reflectivity)
	{
		this.shineDamper=shineDamper;
		this.reflectivity=reflectivity;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	
}
