package com.company.app;

import com.company.utils.CommandLine;
import net.bugs.testhelper.TestHelper;

/**
 * Created by nikolai on 10.01.14.
 */
public class TestManager {
    private static volatile TestManager instance;
    private TestHelper testHelper;
    private CommandLine commandLine;

    private TestManager(String[] args) {
        commandLine = new CommandLine(args);
        testHelper = new TestHelper(commandLine.getDeviceId());
    }

    public static TestManager getInstance(String[] args) {
        if(instance == null)
            synchronized (TestManager.class){
                if(instance == null)
                    instance = new TestManager(args);
            }
        return instance;
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
