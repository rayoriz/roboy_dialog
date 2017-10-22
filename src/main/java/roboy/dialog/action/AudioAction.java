package roboy.dialog.action;

/**
 * Created by laura on 21.10.17.
 */
public class AudioAction implements Action{

    private String audioName;

    /**
     * Constructor.
     *
     * @param audioName The audio file Roboy will play
     */
    public AudioAction(String audioName){
        this.audioName = audioName;
    }

    public String getAudioName(){
        return audioName;
    }
}
