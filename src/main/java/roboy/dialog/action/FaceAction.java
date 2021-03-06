package roboy.dialog.action;

/**
 * Action used if the dialogue manager wants Roboy to express a certain facial expression,
 * like being angry, neutral or moving its lips (speak).
 */
public class FaceAction implements Action
{
	
	private String state;
	private int duration;
	
	/**
	 * Constructor. Duration is set to 1.
	 * 
	 * @param state The facial expression. Possible values: angry, neutral, speak
	 */
	public FaceAction(String state)
	{
		this.state = state;
		this.duration = 1;
	}

	/**
	 * Constructor.
	 * 
	 * @param state The facial expression. Possible values: angry, neutral, speak
	 * @param duration How long Roboy should display the given facial expression
	 */
	public FaceAction(String state, int duration)
	{
		this.state = state;
		this.duration = duration;
	}

	public String getState()
	{
		return this.state;
	}

	public int getDuration()
	{
		return this.duration;
	}

}