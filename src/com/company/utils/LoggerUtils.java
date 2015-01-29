package com.company.utils;

/**
 * Created by nikolai on 10.01.14.
 */
public class LoggerUtils {
    public static final String SPLIT = "::";
    public static final String INFO = "INFO:";
    public static final String ERROR = "ERROR:";

    public static void i(String tag, String msg) {
        String info = INFO + tag + SPLIT + msg;
        System.out.println(info);
    }

    public static void i(String msg) {
        String info = INFO + SPLIT + msg;
        System.out.println(info);
    }

    public static void e(String tag, String msg){
        String info = ERROR + tag + SPLIT + msg;
        System.out.println(info);
    }

    public static void e(String msg){
        String info = ERROR + SPLIT + msg;
        System.out.println(info);
    }
}