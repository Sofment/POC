package com.company.app;

import com.company.enums.ConfigParameter;
import com.company.model.Device;
import com.company.utils.CommandLine;
import com.company.utils.Constant;
import net.bugs.testhelper.TestHelper;

import java.io.File;
import static com.company.utils.LoggerUtils.i;

/**
 * Created by nikolai on 10.01.14.
 */
public class TestManager {
    private static volatile TestManager instance;
    private TestHelper testHelper;
    private ConfigManager configManager;
    private static CommandLine commandLine;
    private Device device;

    private TestManager(CommandLine cmdLine) {
        commandLine = cmdLine;
        device = new Device(commandLine.getDeviceId());
        testHelper = new TestHelper(Constant.CONFIGURATION_FILE_PATH, commandLine.getDeviceId());
        configManager = new ConfigManager();
    }

    public void clearPreviousLaunchResults() {
        i("----Removal of previous results----");
        File file = new File(configManager.getProperty(ConfigParameter.PATH_TOP_SCREEN_SHOT_FOLDER.name()));
        if(file.exists()) {
            if(file.listFiles() != null) {
                for (File childFile : file.listFiles()) {
                    if (childFile.exists()) {
                        i("File " + childFile.getName() + " was deleted");
                        childFile.delete();
                    }
                }
            }
            file.delete();
        }

        File zippedScreenshotsFolder = new File("screenshots.zip");
        if(zippedScreenshotsFolder.exists()) {
            zippedScreenshotsFolder.delete();
        }
    }

    public static TestManager getInstance() {
        return getInstance(commandLine);
    }

    public static TestManager getInstance(CommandLine cmdLine) {
        commandLine = cmdLine;
        if(instance == null)
            synchronized (TestManager.class){
                if(instance == null)
                    instance = new TestManager(commandLine);
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
