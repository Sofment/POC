package com.company.test;

import com.company.app.ConfigManager;
import com.company.app.TestManager;
import com.company.model.Device;
import com.company.utils.ConfigParameter;
import com.company.utils.Constant;
import com.company.utils.Runner;
import net.bugs.testhelper.TestHelper;

/**
 * Created by nikolai on 29.01.2015.
 */
public class Test {
    private TestHelper testHelper;
    private TestManager testManager;
    private Device device;
    private ConfigManager configManager;

    public Test() {
        testManager = TestManager.getInstance();
        testHelper = testManager.getTestHelper();
        device = testManager.getDevice();
        configManager = testManager.getConfigManager();
    }

    public void tc265() {

    }

    public void tc122() {
        if(Runner.isInstalledApk(Constant.PACKAGE_APP, device.getId())) {
            Runner.runProcess(new String[]{"adb", "-s", device.getId(), "uninstall", Constant.PACKAGE_APP}, true);
        }else {
            Runner.runProcess(new String[]{"adb", "-s", device.getId(), "install",
                    configManager.getProperty(ConfigParameter.PATH_TO_APK.name())}, true);
        }
    }

}
