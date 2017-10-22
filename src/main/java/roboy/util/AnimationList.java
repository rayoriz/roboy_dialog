package roboy.util;

/**
 * Encodes the animation names as they should be sent to the ROS face node.
 */
public enum AnimationList {
    PULP_FICTION("pulp_fiction", 3),
    PULP_FICTION_FACE("pulp_fiction_face", 3),
    TERMINATOR("terminator", 5),
    TERMINATOR_FACE("terminator_face", 3),
    TERMINATOR2("terminator2", 3),
    BUGS_BUNNY("bugs_bunny", 3),
    MINIONS("minions", 10),
    BIRTHDAY("happy_birthday", 10);


    public String animationName;
    public int duration;

    AnimationList(String animationName, int duration) {
        this.animationName=animationName;
        this.duration=duration;
    }
}
