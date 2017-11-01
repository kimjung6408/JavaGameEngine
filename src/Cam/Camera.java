package Cam;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import ToolBox.Maths;

public class Camera {
	private static final float FieldOfView=70.0F;
	private static final float NEAR_PLANE=0.1f;
	private static final float FAR_PLANE=2000.0f;
	
	private Matrix4f projMatrix;
	
	private float Pitch, Yaw;
	private Vector3f position;
	
	
	public Camera()
	{
		this.projMatrix=Maths.createProjMatrix(FieldOfView, NEAR_PLANE, FAR_PLANE);
		Pitch=0.0f;
		Yaw=0f;
		position=new Vector3f(0.0f,0.0f,1.0f);
	}
	
	public Matrix4f getProjMatrix()
	{
		return projMatrix;
	}
	
	public void RotateRollPitchYaw(float PitchAngle, float YawAngle)
	{
		pitch(PitchAngle);
		yaw(YawAngle);
	}

	public void pitch(float pitchAngle)
	{
		this.Pitch+=pitchAngle;
	}
	
	public void yaw(float yawAngle)
	{
		this.Yaw+=yawAngle;
	}
	
	public void Translate(float dx, float dy, float dz)
	{
		position.x+=dx;
		position.y+=dy;
		position.z+=dz;
	}
	
	public float getPitch()
	{
		return this.Pitch;
	}
	
	public float getYaw()
	{
		return this.Yaw;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	
	public void setPosition(Vector3f position)
	{
		this.position=position;
	}

	
	public void Update()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			MoveRight(-5.0f);
		}
		else 	if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			MoveRight(5.0f);
		}
		else 	if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			MoveForward(5.0f);
		}
		else 	if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			MoveForward(-5.0f);
		}
		
		if(Mouse.isButtonDown(0))
		{
			Pitch+=(float)Mouse.getDY()/10F;
			Yaw+=(float)Mouse.getDX()/10f;
		}
	}
	
    public void MoveForward(float dist)
    {
    	Matrix4f view=Maths.createViewMatrix(this);
    	
    	Vector3f forward=new Vector3f(-view.m02, -view.m12, -view.m22);
    	
    	position.x+=forward.x*dist;
    	position.y+=forward.y*dist;
    	position.z+=forward.z*dist;
    }
    
    public void MoveUp(float dist)
    {
    	Matrix4f view=Maths.createViewMatrix(this);
    	
    	Vector3f up=new Vector3f(view.m01, view.m11, view.m21);
    	
    	position.x+=up.x*dist;
    	position.y+=up.y*dist;
    	position.z+=up.z*dist;
    	
    }
    
    public void MoveRight(float dist)
    {
    	Matrix4f view=Maths.createViewMatrix(this);
    	Vector3f right=new Vector3f(view.m00, view.m10, view.m20);
    	
    	position.x+=right.x*dist;
    	position.y+=right.y*dist;
    	position.z+=right.z*dist;
    }
}
