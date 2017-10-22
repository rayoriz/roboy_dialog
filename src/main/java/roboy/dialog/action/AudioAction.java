package roboy.dialog.action;

import roboy.util.MovieList;

/**
 * Created by laura on 21.10.17.
 */
public class AudioAction implements Action{

    private MovieList audioName;

    /**
     * Constructor.
     *
     * @param audioName The audio file Roboy will play
     */
    public AudioAction(MovieList audioName){
        this.audioName = audioName;
    }

    public MovieList getAudioName(){
        return audioName;
    }
}
