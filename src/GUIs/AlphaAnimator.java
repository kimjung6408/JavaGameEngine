package GUIs;

public class AlphaAnimator implements GUIAnimator {
	
	float deltaAlpha;
	boolean repeat;
	//0이면 알파 작아짐, 1이면 커짐
	int loop_direction;
	GUIAnimStatus status;
	float alpha;
	
	//speed는 반드시 0.0 초과 1미만의 양수를 입력
	public AlphaAnimator(float speed)
	{
		deltaAlpha=speed;
		loop_direction=0;
		repeat=true;
		status=GUIAnimStatus.STOP;
		alpha=1.0f;
	}
	
	@Override
	public boolean UpdateGUI(GUITexture gui) {
		// TODO Auto-generated method stub
		if(loop_direction==0)
		{
			alpha-=deltaAlpha;
			gui.setAlpha(alpha);
			System.out.println(""+gui.getAlpha());
			if(gui.getAlpha()<=0.0f)
			{
				loop_direction=1;
				
				if(!repeat)
				{
					status=GUIAnimStatus.STOP;
				}
			}
		}
		else
		{
			alpha+=deltaAlpha;
			gui.setAlpha(alpha);
			
			if(gui.getAlpha()>=1.0f)
			{
				loop_direction=0;
				
				if(!repeat)
				{
					status=GUIAnimStatus.STOP;
				}
			}
		}
	
		if(status==GUIAnimStatus.PLAYING)
			return true;
		else
			return false;
	}

	@Override
	public void StopAnimation(GUITexture gui) {
		// TODO Auto-generated method stub
		gui.setAlpha(1.0f);
		status=GUIAnimStatus.STOP;
	}

	@Override
	public void PauseAnimation(GUITexture gui) {
		// TODO Auto-generated method stub
		if(status==GUIAnimStatus.PLAYING)
		{
			status=GUIAnimStatus.PAUSE;
		}
	}

	@Override
	public boolean isRepeating() {
		// TODO Auto-generated method stub
		return repeat;
	}

	@Override
	public void SetRepeat(boolean repeat) {
		// TODO Auto-generated method stub
		this.repeat=repeat;
	}

	@Override
	public float GetCurrentPlayTime(GUITexture gui) {
		// TODO Auto-generated method stub
		return gui.getAlpha();
	}

	@Override
	public GUIAnimStatus GetStatus() {
		// TODO Auto-generated method stub
		return status;
	}

	public void Play()
	{
		status=GUIAnimStatus.PLAYING;
	}
}
