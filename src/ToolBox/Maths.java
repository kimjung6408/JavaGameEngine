package ToolBox;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.*;

import Cam.Camera;

public class Maths {
	
	public static Matrix4f createProjMatrix(float FOV, float NEAR_PLANE, float FAR_PLANE)
	{
		Matrix4f projMat;
		float aspectRatio = (float) Display.getDisplayMode().getWidth() / (float) Display.getDisplayMode().getHeight();
		  float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		  float x_scale = y_scale / aspectRatio;
		  float frustum_length = FAR_PLANE - NEAR_PLANE;

		  projMat = new Matrix4f();
		  projMat.m00 = x_scale;
		  projMat.m11 = y_scale;
		  projMat.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		  projMat.m23 = -1;
		  projMat.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		  projMat.m33 = 0;
		  
		  return projMat;
	}
	
	public static Matrix4f createWorldMatrix(Vector3f Translation, Vector3f Rotation, float scale)
	{
		Matrix4f matrix=new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(Translation, matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(Rotation.x), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(Rotation.y), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(Rotation.z), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		
		return matrix;
	}
	
	public static Matrix4f createWorldMatrix(Vector2f Translation, Vector3f Rotation, Vector2f scale)
	{
		Matrix4f matrix=new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(Translation.x, Translation.y, 0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(Rotation.x), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(Rotation.y), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float)Math.toRadians(Rotation.z), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 0), matrix, matrix);
		
		return matrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera)
	{
		Matrix4f viewMat=new Matrix4f();
		viewMat.setIdentity();
		
		Matrix4f.rotate(-(float)Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), viewMat, viewMat);
		Matrix4f.rotate(-(float)Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), viewMat, viewMat);
		
		Vector3f camPos=camera.getPosition();
		Vector3f camReversePos=new Vector3f(-camPos.x,-camPos.y, -camPos.z);
		
		Matrix4f.translate(camReversePos, viewMat, viewMat);
		
		return viewMat;
	}
}
