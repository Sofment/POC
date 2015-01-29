package com.company.test;

import com.company.app.ConfigManager;
import com.company.app.TestManager;
import com.company.enums.TestNamesEnum;
import com.company.model.Device;
import com.company.test.testcase.Description;
import com.company.test.testcase.TestCaseHelper;
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
        } else if(testName.equalsIgnoreCase(TestNamesEnum.TC122.name())) {
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
        i(Description.TC122);

        TestCaseHelper testCaseHelper = new TestCaseHelper(testHelper);

        if(Runner.isInstalledApk(Constant.PACKAGE_APP, device.getId())) {
            testHelper.getAdb().unInstall(Constant.PACKAGE_APP);
//            Runner.runProcess(new String[]{"adb", "-s", device.getId(), "uninstall", Constant.PACKAGE_APP}, true);
        }
        testHelper.getAdb().push("entrada-5.3.32-aligned.apk", "/sdcard/");
        testHelper.getSettingsHelper().openInstallationWindow("/sdcard/entrada-5.3.32-aligned.apk");
        if(testHelper.waitForExistsByText("Settings", 3000, false)) {
            testCaseHelper.enableInstallationFromUnknownSources();
            if(isFirstLaunch) {
                isFirstLaunch = false;
                tc122();
                return;
            }
            i("Can not enable settings to install application from unknown sources");
            testCaseHelper.takeScreenShot("testcase_fail");
            System.exit(0);
        }

        testCaseHelper.waitTextAndClick("next", false);
        testCaseHelper.waitTextAndClick("Install", true);
        if(testHelper.waitForExistsByText("installing", 10000, false)) {
            i("installing dialog is appeared");
            testCaseHelper.takeScreenShot("installing_dialog_pass");
        } else {
            i("installing dialog did not appear");
            testCaseHelper.takeScreenShot("installing_dialog_fail");
            System.exit(0);
        }


        if(testHelper.waitForExistsByText("App installed", 10000, false)) {
            i("install completed dialog is appeared");
            testCaseHelper.takeScreenShot("install_completed_dialog_pass");
        } else {
            i("install completed dialog did not appear");
            testCaseHelper.takeScreenShot("install__completed_dialog_fail");
            System.exit(0);
        }

        View done = testHelper.getViewByText("done", false, false);
        done.click();

        testHelper.pressHome();
        if(testCaseHelper.findEntradaApplication(testHelper.getCurrentViews(android.widget.TextView.class))){
            i("Entrada application is installed, icon is found");
            testCaseHelper.takeScreenShot("entrada_icon_is_found");
        } else {
            i("Entrada application is installed, icon is not found");
            testCaseHelper.takeScreenShot("entrada_icon_is_not_found");
        }

    }

}
