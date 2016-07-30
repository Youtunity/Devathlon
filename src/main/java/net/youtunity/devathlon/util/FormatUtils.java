package net.youtunity.devathlon.util;

/**
 * Created by thecrealm on 30.07.16.
 */
public class FormatUtils {

    public static String format(int time) {
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;
        return String.format("{}:{}", minutes, seconds);
    }
}
