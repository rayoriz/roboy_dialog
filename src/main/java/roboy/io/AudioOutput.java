package roboy.io;

import roboy.dialog.action.Action;
import roboy.dialog.action.AudioAction;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by laura on 21.10.17.
 *
 * To output custom audio files to roboy's speaker
 */
public class AudioOutput implements OutputDevice {
    // Implementation from: http://www.jsresources.org/examples/SimpleAudioPlayer.java.html

    private static final int AUDIO_EXTERNAL_BUFFER_SIZE = 128000;
    @Override
    public void act(List<Action> actions) {
        for(Action a : actions){
            if(a instanceof AudioAction){
                final String audioFilePath  = ((AudioAction) a).getAudioName().filePath;
                File soundFile = new File(audioFilePath);
                AudioInputStream audioInputStream = null;
                try
                {
                    audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                }
                catch (Exception e)
                {
                    System.out.println("AUDIO input stream setup FAILED:");
                    return;
                }
                AudioFormat audioFormat = audioInputStream.getFormat();
                SourceDataLine line = null;
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                try
                {
                    line = (SourceDataLine) AudioSystem.getLine(info);
                    line.open(audioFormat);
                }
                catch (LineUnavailableException e)
                {
                    System.out.println("AUDIO line setup FAILED:");
                    return;
                }
                catch (Exception e)
                {
                    System.out.println("AUDIO line setup FAILED:");
                    return;
                }
                line.start();
                int	nBytesRead = 0;
                byte[]	abData = new byte[AUDIO_EXTERNAL_BUFFER_SIZE];
                while (nBytesRead != -1)
                {
                    try
                    {
                        nBytesRead = audioInputStream.read(abData, 0, abData.length);
                    }
                    catch (IOException e)
                    {
                        System.out.println("AUDIO output FAILED:");
                        return;
                    }
                    if (nBytesRead >= 0)
                    {
                        int	nBytesWritten = line.write(abData, 0, nBytesRead);
                    }
                }
                line.drain();
                line.close();
            }
        }
    }
}
