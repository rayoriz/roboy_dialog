package roboy.util;

/**
 * Created by laura on 21.10.17.
 */
public enum AudioFileMapping {
    PULP_FICTION("pulp_fiction", "/resources/audio/pulpfiction.wav"),
    TERMINATOR("terminator", "/resources/audio/terminator.wav"),
    BUGS_BUNNY("bugs_bunny", "/resources/audio/bugs_bunny.wav");

    public String audioType;
    public String filePath;

    AudioFileMapping(String audioType, String filePath) {
        this.audioType=audioType;
        this.filePath=filePath;
    }
}
