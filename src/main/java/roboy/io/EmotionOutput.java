package roboy.io;

import java.util.List;
import java.util.concurrent.TimeUnit;

import roboy.dialog.action.Action;
import roboy.dialog.action.FaceAction;
import roboy.ros.RosMainNode;

/**
 * Roboy's facial expression output.
 * Athough nothing changed in this code, the service called is different from the "real" DM.
 */
public class EmotionOutput implements OutputDevice 
{
	private RosMainNode rosMainNode;

	public EmotionOutput (RosMainNode node){
		this.rosMainNode = node;
	}

	@Override
	public void act(List<Action> actions) {
		for (Action a : actions) {
			if (a instanceof FaceAction) {
				act(a);
			}
		}
	}

	public void act(Action action_raw) {
		if (action_raw instanceof FaceAction) {
			FaceAction action = (FaceAction) action_raw;
			// Differentiate between movie animations -> must wait until they are finished.
			if(action.animate) {
				System.out.print(((FaceAction) action).getState());
				rosMainNode.ShowEmotion(action.getState());
				int wait = action.animation.duration;
				try {
					TimeUnit.SECONDS.sleep(wait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else // Removed condition as all durations are by default 1.
			{
				System.out.print(((FaceAction) action).getState());
				rosMainNode.ShowEmotion(((FaceAction) action).getState());
			}

		}
	}

}
