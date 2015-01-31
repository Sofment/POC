package com.company.utils;

import com.company.model.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.company.utils.LoggerUtils.i;

/**
 * Created by nikolai on 13.12.2014.
 */
public class CommandLine {
    private enum State{HELP, COMMAND};
    private State state = State.COMMAND;
    private String[] args;
    private String deviceId;
    private String testName;
    private List<Pair> listParameters = new ArrayList<Pair>();

    public static String help_msg = "\nParameters for Entrada.jar\n" +
            " -e deviceId       \t - directs command to the device or emulator with the given serial number or qualifier." + "\n" +
            " -e testName       \t - name of the test that you want to run" + "\n" +
            "For example: java -jar Entrada.jar " +
            "-e deviceId 08f6df9a" +
            "-e testName all" + "\n";

    public static String error_msg = "\nUnknown command\n" +"Type 'java -jar Entrada.jar -e help' for usage.";

    public CommandLine(String[] args) {
        this.args = args;
        if (args != null)
            parseCommandLine();
    }

    public boolean isSuccessfulParameters() {
        if(getDeviceId() == null || getTestName() == null){
            i(getState() != State.HELP ? error_msg : "");
            return false;
        }
        return true;
    }

    private void parseCommandLine() {
        String arguments = Arrays.toString(args);
//        arguments = validateArgsCount(arguments);
        arguments = arguments.replace("[", "");
        arguments = arguments.replace("]", "");
        arguments = arguments.replaceAll(",", "");
        i("Arguments:" + arguments);
        if(arguments.contains("-e help")) {
            i(help_msg);
            setState(State.HELP);
            return;
        }
        initializePairs(arguments.split("-e"));
        parseDeviceId();
        parseTestName();
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    private void initializePairs(String[] lines){
        for(String part : lines){
            listParameters.add(new Pair(part.trim().split(" ")));
        }
    }

    private String validateArgsCount(String arguments) {
        if(args.length % 3 != 0) {
            i("Looks like you put wrong parameters.\n" +
                    "usage: \n" +
                    "-e key value");
        }
        int validArgsCount = args.length / 3;
        int currentArgsCount = 0;
        while (arguments.contains("-e")) {
            arguments = arguments.replace("-e", "");
            currentArgsCount ++;
        }
        if(currentArgsCount != validArgsCount) {
            i("Looks like you put wrong parameters.\n" +
                    "usage: \n" +
                    "-e key value");
        }
        return arguments;
    }

    private String searchValue(String key) {
        for(Pair pair : listParameters){
            if(pair.getKey().equalsIgnoreCase(key))
                return pair.getValue();
        }
        return null;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getTestName() {
        return this.testName;
    }

    public void parseDeviceId() {
        this.deviceId = searchValue(Constant.DEVICE_ID);
    }

    public void parseTestName() {
        this.testName = searchValue(Constant.TEST_NAME);
    }

    public static class Constant {
        public static final String DEVICE_ID = "deviceId";
        public static final String TEST_NAME = "testName";
    }
}