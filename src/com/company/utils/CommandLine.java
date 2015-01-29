package com.company.utils;

import java.util.Arrays;

import static com.company.utils.LoggerUtils.i;

/**
 * Created by nikolai on 13.12.2014.
 */
public class CommandLine {
    private String[] args;
    private String deviceId;
    private String[] parameters;

    public static String message = "----------------------------------------------------------------------\n" +
            "AutomationTestRail.jar parameters:\n" +
            "(optional)\t -e uninstallApp       \t value = true or false; default value = false;" + "\n" +
            "(optional)\t -e uninstallApk       \t value = true or false; default value = false;" + "\n" +
            "(optional)\t -e reinstallApp       \t value = true or false; default value = false;" + "\n" +
            "(optional)\t -e reinstallTestApk   \t value = true or false; default value = false;" + "\n" +
            "(optional)\t -e countDevices       \t value = [1, *]; default value = 1;" + "\n" +
            "(optional)\t -e type               \t value = rail or false; default value = rail;" + "\n" +
            "(optional)\t -e testCase           \t value = caseId; default value = 0;" + "\n" +

            "For example: java -jar AutomationTestRail.jar " +
            "-e type rail " +
            "-e countDevices 1 " +
            "-e uninstallApp true " +
            "-e uninstallApk true " +
            "-e reinstallApp true " +
            "-e reinstallTestApk true " + "\n" +
            "----------------------------------------------------------------------";

    /**
     *
     * @param args
     */
    public CommandLine(String[] args) {
        this.args = args;
        if (args != null)
            parseCommandLine();
    }

    private void parseCommandLine() {
        String arguments = Arrays.toString(args);

        arguments = validateArgsCount(arguments);
        arguments = arguments.replace("[", "");
        arguments = arguments.replace("]", "");
        arguments = arguments.replaceAll(",", "");

        i("Arguments: " + arguments);
        parameters = arguments.split(" ");
        setDeviceId(null);
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
        for (int i = 0; i < parameters.length; i++) {
            if(parameters[i].equals(key))
                return parameters[i+1];
        }
        return null;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        if(deviceId != null) {
            this.deviceId = deviceId;
        } else {
            this.deviceId = searchValue(Constant.deviceId);
        }
    }

    public static class Constant {
        public static final String deviceId = "deviceId";
    }
}
