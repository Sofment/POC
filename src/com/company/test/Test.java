package com.company.test;

import com.company.app.ConfigManager;
import com.company.app.TestManager;
import com.company.enums.TestNamesEnum;
import com.company.model.Device;
import com.company.test.testcase.Description;
import com.company.test.testcase.TestCaseHelper;
import com.company.utils.ConfigParameter;
import com.company.utils.Constant;
import com.company.utils.Runner;
import net.bugs.testhelper.TestHelper;
import net.bugs.testhelper.view.View;

import java.util.ArrayList;

import static net.bugs.testhelper.helpers.LoggerUtil.i;

/**
 * Created by nikolai on 29.01.2015.
 */
public class Test {
    private TestHelper testHelper;
    private TestManager testManager;
    private Device device;
    private ConfigManager configManager;
    private boolean isFirstLaunch = true;

    public Test() {
        testManager = TestManager.getInstance();
        testHelper = testManager.getTestHelper();
        device = testManager.getDevice();
        configManager = testManager.getConfigManager();
    }

    public void startTest(String testName) {
        if(testName.equalsIgnoreCase(TestNamesEnum.TC265.name())) {
            this.tc265();
        } else if(testName.equalsIgnoreCase(TestNamesEnum.TC121.name())) {
            this.tc122();
        }
    }

    public void tc265() {
        i(Description.TC265);
        if(!testHelper.waitForExistsByDescriptor("Apps", 5000)) {
            testHelper.pressHome();
        }
        if(!testHelper.waitForExistsByDescriptor("Apps", 10000)) {
            i("Apps button is not found");
            System.exit(0);
        }

        TestCaseHelper testCaseHelper = new TestCaseHelper(testHelper);

        View appsButton = testHelper.getViewByDescriptor("Apps", false);
        i("click on Apps button");
        appsButton.click();

        ArrayList<View> views = testHelper.getCurrentViews(android.widget.TextView.class);
        if(!testCaseHelper.findEntradaApplication(views)) {
            i("Entrada application is not installed.\n" +
                    "installing...");
            testHelper.getAdb().install("entrada-5.3.32-aligned.apk");
            i("restart test case tc265");
            if(isFirstLaunch) {
                isFirstLaunch = false;
                tc265();
            } else {
                i("can not install Entrada application");
                System.exit(0);
            }
        } else {
            i("Entrada application if found");
            testCaseHelper.uninstallEntrada();
        }
    }

    public void tc122() {

        if(Runner.isInstalledApk(Constant.PACKAGE_APP, device.getId())) {
            testHelper.getAdb().unInstall(Constant.PACKAGE_APP);
//            Runner.runProcess(new String[]{"adb", "-s", device.getId(), "uninstall", Constant.PACKAGE_APP}, true);
        }else {
            testHelper.getAdb().install(Constant.PACKAGE_APP);
//            Runner.runProcess(new String[]{"adb", "-s", device.getId(), "install",
//                    configManager.getProperty(ConfigParameter.PATH_TO_APK.name())}, true);
        }
    }

}
