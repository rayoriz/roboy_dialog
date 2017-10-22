package roboy.util;

/**
 * Encodes the animation names as they should be sent to the ROS face node.
 */
public enum AnimationList {
    PULP_FICTION("pulp_fiction", 5),
    TERMINATOR("terminator", 5),
    TERMINATOR2("terminator2", 5),
    BUGS_BUNNY("bugs_bunny", 5),
    MINIONS("minions", 5),
    BIRTHDAY("birthday", 15);


    public String animationName;
    public int duration;

    AnimationList(String animationName, int duration) {
        this.animationName=animationName;
        this.duration=duration;
    }
}
