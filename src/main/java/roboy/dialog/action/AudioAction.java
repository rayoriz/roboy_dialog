package roboy.dialog.action;

import roboy.io.AudioOutput;
import roboy.util.AudioFileMapping;

/**
 * Created by laura on 21.10.17.
 */
public class AudioAction {

    private AudioFileMapping audioName;

    /**
     * Constructor.
     *
     * @param audioName The audio file Roboy will play
     */
    public AudioAction(AudioFileMapping audioName){
        this.audioName = audioName;
    }

    public AudioFileMapping getAudioName(){
        return audioName;
    }
}
