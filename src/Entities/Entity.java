package Entities;

import org.lwjgl.util.vector.Vector3f;

import Models.TexturedModel;

public class Entity {
	private TexturedModel model;
	private Vector3f position;
	private Vector3f rotation;
	private float scale;
	
	public Entity(TexturedModel model, Vector3f position, Vector3f rotation, float scale)
	{
		this.model=model;
		this.position=position;
		this.rotation=rotation;
		this.scale=scale;
	}
	
	public Entity()
	{
		this.model=null;
		this.position=new Vector3f(0,0,0);
		this.rotation=new Vector3f(0,0,0);
		this.scale=1.0f;
	}
	
	public Entity(TexturedModel model)
	{
		this.model=model;
		this.position=new Vector3f(0,0,0);
		this.rotation=new Vector3f(0,0,0);
		this.scale=1.0f;
	}
	
	public void Translate(float dx, float dy, float dz)
	{
			position.x+=dx;
			position.y+=dy;
			position.z+=dz;
	}
	
	public void Rotate(float dx, float dy, float dz)
	{
		Vector3f.add(rotation, new Vector3f(dx, dy, dz), rotation);
	}
	

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	
}
