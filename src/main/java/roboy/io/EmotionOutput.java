package roboy.io;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import roboy.dialog.action.Action;
import roboy.dialog.action.FaceAction;
import roboy.ros.RosMainNode;
import roboy.util.AnimationList;

/**
 * Roboy's facial expression output.
 * Athough nothing changed in this code, the service called is different from the "real" DM.
 */
public class EmotionOutput implements OutputDevice 
{
	private RosMainNode rosMainNode;
	private CerevoiceOutput voice;
	private List<String> pre;

	public EmotionOutput (RosMainNode node){
		this.rosMainNode = node;
	}

	public EmotionOutput (RosMainNode node, CerevoiceOutput voice){
		this.rosMainNode = node;
		this.voice = voice;
		pre = new ArrayList<>();
		pre.add("I know just the right thing.");
		pre.add("Have you seen this?");
		pre.add("I got this.");
		pre.add("I know a trick, look!");
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
				if(voice != null) {
					if(action.animation != AnimationList.PULP_FICTION_FACE &&
							action.animation != AnimationList.TERMINATOR2) {
						String toSay = pre.get((int) (Math.random()*pre.size()));
						voice.say(toSay);
					}
				}
				System.out.print(((FaceAction) action).getState());
				rosMainNode.ShowEmotion(action.getState());
				if(action.animation.equals(AnimationList.TERMINATOR2)){
					System.exit(0);
				}
//				int wait = action.animation.duration;
//				try {
//					TimeUnit.SECONDS.sleep(wait);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}
			else // Removed condition as all durations are by default 1.
			{
				System.out.print(((FaceAction) action).getState());
				rosMainNode.ShowEmotion(((FaceAction) action).getState());
			}
		}
	}

}
