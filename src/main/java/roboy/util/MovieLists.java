package roboy.util;

/**
 * Created by roboy on 10/21/17.
 *
 * Temproary class for roboy demo, can be changed to a lookup file with movie names later
 */
public enum MovieLists  {

       PULP("Say what one more time m#@$@#$r!"),
       BUGS("Hey, Whats up doc?"),
       TERMINATOR("Hasta la vista baby!");

    public String type;

    MovieLists(String type) {
        this.type=type;
    }
}
