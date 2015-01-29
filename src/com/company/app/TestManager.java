package com.company.app;

import com.company.model.Device;
import com.company.utils.CommandLine;
import net.bugs.testhelper.TestHelper;

/**
 * Created by nikolai on 10.01.14.
 */
public class TestManager {
    private static volatile TestManager instance;
    private static String[] arguments = null;
    private TestHelper testHelper;
    private ConfigManager configManager;
    private CommandLine commandLine;
    private Device device;

    private TestManager(String[] args) {
        commandLine = new CommandLine(args);
        device = new Device(commandLine.getDeviceId());
        testHelper = new TestHelper(commandLine.getDeviceId());
        configManager = new ConfigManager();
    }

    public static TestManager getInstance() {
        return getInstance(arguments);
    }

    public static TestManager getInstance(String[] args) {
        arguments = args;
        if(instance == null)
            synchronized (TestManager.class){
                if(instance == null)
                    instance = new TestManager(arguments);
            }
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public Device getDevice() {
        return device;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    public TestHelper getTestHelper() {
        return testHelper;
    }

    public static String getUserDir(){
        return System.getProperty("user.dir");
    }
}
