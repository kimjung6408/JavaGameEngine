package GUIs;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import GUIs.GUIAnimator.GUIAnimStatus;
import RenderEngine.DisplayManager;

public class GUITexture {
	private int textureID;
	private Vector2f position;
	private Vector2f scale;
	private Vector3f rotation;
	private float alpha;
	private GUIAnimator animator;
	
	public GUITexture(int textureID, Vector2f position, Vector2f scale) {
		super();
		this.textureID = textureID;
		this.position = position;
		this.scale = scale;
		this.rotation=new Vector3f(0,0,0);
		this.alpha=1.0f;
		this.animator=null;
	}
	
	public GUITexture(int textureID, Vector2f position, Vector2f scale, GUIAnimator animator) {
		super();
		this.textureID = textureID;
		this.position = position;
		this.scale = scale;
		this.rotation=new Vector3f(0,0,0);
		this.alpha=1.0f;
		this.animator=animator;
	}
	
	public void setAnimation(GUIAnimator animator)
	{
		this.animator=animator;
	}
	
	public void UpdateGUI()
	{
		if(animator!=null && animator.GetStatus()==GUIAnimStatus.PLAYING)
		{
			animator.UpdateGUI(this);
		}
	}
	
	public void PlayAnimation()
	{
		animator.Play();
	}
	
	public void ResumeAnimation()
	{
		if(animator.GetStatus()==GUIAnimStatus.PAUSE)
		animator.UpdateGUI(this);
	}
	
	public void PauseAnimation()
	{
		if(animator!=null)
		animator.PauseAnimation(this);
	}
	
	public void StopAnimation()
	{
		if(animator!=null)
		animator.StopAnimation(this);
	}
	
	public GUIAnimator getAnimator()
	{
		return animator;
	}
	

	public int getTextureID() {
		return textureID;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	public void setPosition(Vector2f position)
	{
		this.position=position;
	}
	
	public void setScale(float xscale, float yscale)
	{
		this.scale=new Vector2f(xscale, yscale);
	}
	
	public void Translate(float dx, float dy)
	{
		position.x+=dx;
		position.y+=dy;
	}
	
	public void Rotate(float angleX, float angleY ,float angleZ)
	{
		rotation.x+=angleX;
		rotation.y+=angleY;
		rotation.z+=angleZ;
		
		if(rotation.x>=360.0f)
		rotation.x-=360.0f;
		else if(rotation.x<=-360.0f)
		{
			rotation.x+=360.0f;
		}
		
		if(rotation.y>=360.0f)
		{
			rotation.y-=360.0f;
		}
		else if(rotation.y<=-360.0f)
		{
			rotation.y+=360.0f;
		}
		
		if(rotation.z>=360.0f)
		{
			rotation.z-=360.0f;
		}
		else if(rotation.z<=-360.0f)
		{
			rotation.z+=360.0f;
		}
		
		
	}
	
	public Vector3f getRotation()
	{
		return this.rotation;
	}
	
	public void setBoundary(Vector2f left_top, Vector2f right_bottom)
	{
		this.position=new Vector2f((left_top.x+right_bottom.x)/2f,(left_top.y+right_bottom.y)/2f);
		
		float x_scale=Math.abs((right_bottom.x-left_top.x))/(float)DisplayManager.WIDTH;
		float y_scale=Math.abs((right_bottom.y-left_top.y))/(float)DisplayManager.HEIGHT;
		
		this.scale=new Vector2f(x_scale, y_scale);
	}
	
	public void setAlpha(float alpha)
	{
		this.alpha=alpha;
	}
	
	public float getAlpha()
	{
		return alpha;
	}
	
	public void addAlpha(float d_alpha)
	{
		this.alpha+=d_alpha;
	}
}
