package roboy.dialog.personality.states;

import roboy.linguistics.Linguistics;
import roboy.linguistics.sentenceanalysis.Interpretation;
import roboy.util.Lists;
import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.Service;
import edu.wpi.rail.jrosbridge.services.ServiceRequest;
import org.json.*;

public abstract class AbstractBooleanState implements State{

	protected State success;
	protected State failure;
	
	public State getSuccess() {
		return success;
	}
	public void setSuccess(State success) {
		this.success = success;
	}
	public State getFailure() {
		return failure;
	}
	public void setFailure(State failure) {
		this.failure = failure;
	}
	
	@Override
	public Reaction react(Interpretation input) {
		String sentence = (String) input.getFeatures().get(Linguistics.SENTENCE);
		boolean successful = determineSuccess(input);
		if(successful){
			return new Reaction(success);
		} else {
			return new Reaction(failure,Lists.interpretationList(new Interpretation(callGenerativeModel(sentence))));
		}
	}
	
	abstract protected boolean determineSuccess(Interpretation input);
	
	protected String callGenerativeModel(String sentence){
		
		Ros ros = new Ros("localhost");
		ros.connect();
		Service GenerativeModel = new Service(ros, "/roboy/gnlp_predict", "generative_nlp/seq2seq_predict");
		System.out.println(sentence);
	    ServiceRequest request = new ServiceRequest("{\"text_input\": " + "\"" + sentence + "\"}");
	    String response = GenerativeModel.callServiceAndWait(request).toString();
	    
	    JSONObject obj = new JSONObject(response);
	    String text = obj.getString("text_output");
		ros.disconnect();
		return text;
	}
}