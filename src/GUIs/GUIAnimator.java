package GUIs;

public interface GUIAnimator {
	public enum GUIAnimStatus{PAUSE, STOP, PLAYING};
	
	public boolean UpdateGUI(GUITexture gui);
	public void StopAnimation(GUITexture gui);
	public void PauseAnimation(GUITexture gui);
	public boolean isRepeating();
	public void SetRepeat(boolean repeat);
	public float GetCurrentPlayTime(GUITexture gui);
	public GUIAnimStatus GetStatus();
	public void Play();
	
}
