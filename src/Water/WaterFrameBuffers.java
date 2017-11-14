package Water;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public class WaterFrameBuffers {
	protected static final int REFLECTION_WIDTH=320;
	private static final int REFLECTION_HEIGHT=180;
	
	protected static final int REFRACTION_WIDTH=1280;
	protected static final int REFRACTION_HEIGHT=720;
	
	private int ReflectFrameBuffer;
	private int ReflectTexture;
	private int ReflectDepthBuffer;
	
	private int RefractFrameBuffer;
	private int RefractTexture;
	private int RefractDepthTexture;
	
	public WaterFrameBuffers()
	{
		InitReflectFBO();
		InitRefractFBO();
	}
	
	public void CleanUp()
	{
		GL30.glDeleteFramebuffers(ReflectFrameBuffer);
		GL11.glDeleteTextures(ReflectTexture);
		GL30.glDeleteRenderbuffers(ReflectDepthBuffer);
		GL30.glDeleteFramebuffers(RefractFrameBuffer);
		GL11.glDeleteTextures(RefractTexture);
		GL11.glDeleteTextures(RefractDepthTexture);
	}
	
	public void BindReflectFBO()
	{
		BindFBO(ReflectFrameBuffer, REFLECTION_WIDTH, REFLECTION_HEIGHT);
	}
	
	public void BindRefractFBO()
	{
		BindFBO(RefractFrameBuffer,REFRACTION_WIDTH, REFRACTION_HEIGHT);
	}
	
	public void UnbindCurrentFBO()
	{
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	public int getReflectTexture()
	{
		return ReflectTexture;
	}
	
	public int getRefractTexture()
	{
		return RefractTexture;
	}
	
	public int getRefractDepthTexture()
	{
		return RefractDepthTexture;
	}
	
	public void InitReflectFBO()
	{
		ReflectFrameBuffer=CreateFBO();
		ReflectTexture=CreateTextureAttachment(REFLECTION_WIDTH, REFLECTION_HEIGHT);
		ReflectDepthBuffer=CreateDepthBufferAttachment(REFLECTION_WIDTH, REFLECTION_HEIGHT);
		
		UnbindCurrentFBO();
	}

	public void InitRefractFBO()
	{
		RefractFrameBuffer=CreateFBO();
		RefractTexture=CreateTextureAttachment(REFRACTION_WIDTH, REFRACTION_HEIGHT);
		RefractDepthTexture=CreateDepthTextureAttachment(REFRACTION_WIDTH, REFRACTION_HEIGHT);
		
		UnbindCurrentFBO();
		
	}
	private int CreateFBO()
	{
		int fbo=GL30.glGenFramebuffers();
		
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
		
		//현재 bind된 framebuffer에서 렌더링을 호출할 때, color_attachment 0에 그린다.
		GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
		
		return fbo;
	}

	private int CreateTextureAttachment(int width, int height)
	{
		int texture=GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		
		//온통 까만 텍스쳐를 저장
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)null);
	
		//파라미터 설정.
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
		//fbo의 color_attachment0번 포인트에 텍스쳐를 연결한다.
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, texture, 0);
		
		return texture;
	}
	
	private int CreateDepthTextureAttachment(int width, int height)
	{
		int texture=GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		
		//온통 까만 텍스쳐를 저장
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT32, width, height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer)null);
	
		//파라미터 설정.
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
		//fbo의 depth attachment 포인트에 텍스쳐를 연결한다.
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, texture, 0);
		
		return texture;
	}

	private int CreateDepthBufferAttachment(int width, int height)
	{
		int depthbuffer=GL30.glGenRenderbuffers();
		
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, depthbuffer);
		
		GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL11.GL_DEPTH_COMPONENT, width, height);
	
		//renderbuffer object를 framebuffer object의 depth attachment 포인트에 붙인다.
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, depthbuffer);
		
		return depthbuffer;
	}

	
	private void BindFBO(int fbo, int width, int height)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); //initialize
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
		GL11.glViewport(0, 0, width, height);
	}
}
