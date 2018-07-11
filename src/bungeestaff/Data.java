package bungeestaff;

import java.util.HashMap;
import java.util.Map;

public class Data {

    private static Map<String, String> ranks = new HashMap<String, String>();
    private static Map<String, String> prefix = new HashMap<String, String>();
    private static String online;


    public static Map<String, String> getPrefix() {
        return prefix;
    }

    public static Map<String, String> getRanks() {
        return ranks;
    }

    public static String getOnline() {
        return online;
    }
}
