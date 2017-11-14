package RenderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	public static final int WIDTH=1280;
	public static final int HEIGHT=720;
	public static final int FPS_CAP=120;
	
	private static long lastFrameTime;
	private static float delta;

	public static void createDisplay(){
		
		//openGL Version 320
		ContextAttribs attribs=new ContextAttribs(3,2)
				.withForwardCompatible(true)
				.withProfileCompatibility(true);
		
		try
		{
			//Determine size of display
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			
		}catch(LWJGLException e)
		{
			e.printStackTrace();
		}
		
		//set viewport
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime=getCurrentTime();
	}
	
	public static void updateDisplay(){
		
		//120 FPS 주사율에 디스플레이를 맞춘다.
		Display.sync(120);
		Display.update();
		long currentFrameTime=getCurrentTime();
		delta=(currentFrameTime-lastFrameTime)/1000f;
		
		lastFrameTime=currentFrameTime;
	}
	
	public static void closeDisplay(){}
	
	private static long getCurrentTime()
	{
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
	
	
	public static float DeltaTime()
	{
		return delta;		
	}
}
