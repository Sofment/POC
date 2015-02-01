package com.company.test;

import com.company.app.ConfigManager;
import com.company.app.TestManager;
import com.company.enums.ConfigParameter;
import com.company.enums.TestNamesEnum;
import com.company.model.Device;
import com.company.model.TestCase;
import com.company.report.EmailNotification;
import com.company.report.Report;
import com.company.test.testcase.Description;
import com.company.test.testcase.ExpectedResult;
import com.company.test.testcase.TestCaseHelper;
import com.company.utils.Constant;
import net.bugs.testhelper.TestHelper;
import net.bugs.testhelper.helpers.Zipper;
import net.bugs.testhelper.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static net.bugs.testhelper.helpers.LoggerUtil.i;

/**
 * Created by nikolai on 29.01.2015.
 */
public class Test {
    private TestHelper testHelper;
    private TestManager testManager;
    private TestCaseHelper testCaseHelper;
    private Device device;
    private ConfigManager configManager;
    private boolean isFirstLaunch = true;
    private ArrayList<TestCase> testCases = new ArrayList<TestCase>(){};
    private TestCase testCase;

    public Test() {
        testManager = TestManager.getInstance();
        testHelper = testManager.getTestHelper();
        testCaseHelper = new TestCaseHelper(testHelper);
        device = testManager.getDevice();
        configManager = testManager.getConfigManager();
    }

    public void startTest(String testName) {
        if(testName == null) return;
        if(testName.equalsIgnoreCase(TestNamesEnum.TC265.name())) {
            this.tc265();
        } else if(testName.equalsIgnoreCase(TestNamesEnum.TC122.name())) {
            this.tc122();
        } else if(testName.equalsIgnoreCase(TestNamesEnum.TC266.name())) {
            this.tc266();
        }  else if(testName.equalsIgnoreCase(TestNamesEnum.TC267.name())) {
            this.tc267();
        } else if(testName.equalsIgnoreCase(TestNamesEnum.ALL.name())) {
            tc265();
            tc122();
            tc266();
            tc267();
        }

        Zipper zipper = testHelper.getZipper();
        EmailNotification emailNotification = new EmailNotification(testHelper, null);
        String subject = configManager.getProperty(ConfigParameter.SUBJECT.name());
        Report report = new Report(testCases, testHelper);
        String body = report.getHtmlReport();
        String[] recipients = configManager.getSentToEmails();

        File zippedScreenshotsFolder = null;
        File screenShotDir = new File(TestManager.ScreenShotFolder);
        if(screenShotDir.exists()) {
            zipper.zipDirectory(screenShotDir, TestManager.ScreenShotFolder + ".zip");
            zippedScreenshotsFolder = new File(TestManager.ScreenShotFolder + ".zip");
            if(zippedScreenshotsFolder.exists()) {
                emailNotification.addFileToAttachment(zippedScreenshotsFolder);
            }
        }

        if(emailNotification.sendEmail(subject, body, recipients)){
            i("Email was sent to " + Arrays.toString(recipients));
        }else {
            i("Email wasn't sent to " + Arrays.toString(recipients));
        }
    }

    private void tc267() {
        i(Description.TC267);
        Constant.Temp.TEST_ID = "TC267";

        testCase = new TestCase("TC267", "android-entrada_Install_Create PIN");

        if(!testCaseHelper.reinstallEntradaViaAdb()) {
            return;
        }
        testCaseHelper.startEntrada();
        View editText = null;
        if(testHelper.waitForExistsByClass(android.widget.EditText.class.getName(), 20000, false)) {
            editText = testHelper.getViewByClass(android.widget.EditText.class.getName(), false, false);
            if(editText.getPackageName().equals(Constant.PACKAGE_APP)) {
                i("Choose pin window is displayed");
                testCase.addExpectedResult(new ExpectedResult("Choose PIN window is displayed", true));
            } else {
                i("Choose pin window is not displayed");
                testCase.addExpectedResult(new ExpectedResult("Choose PIN window is not displayed", false));
                return;
            }
        } else {
            i("Choose pin window is not displayed");
            testCase.addExpectedResult(new ExpectedResult("Choose PIN window is displayed", false));
            return;
        }

        editText.click();

        testHelper.inputText(testCaseHelper.pin);
        editText = testHelper.getViewByClass(android.widget.EditText.class.getName());
        View arrow = testHelper.getNextView(editText);

        if(arrow.exists()) {
            i("click on arrow");
            arrow.click();
        } else {
            i("arrow is not found");
            return;
        }
        editText = testHelper.getViewByClass(android.widget.EditText.class.getName());
        i("Tap into Confirm PIN");
        editText.click();
        testHelper.inputText(testCaseHelper.pin);

        editText = testHelper.getViewByClass(android.widget.EditText.class.getName());
        arrow = testHelper.getNextView(editText);
        i("Confirm PIN window is displayed");
        testCase.addExpectedResult(new ExpectedResult("Confirm PIN window is displayed", true));

        if(arrow.exists()) {
            i("click on arrow");
            arrow.click();
        } else {
            i("arrow is not found");
            return;
        }

        if(testHelper.waitForExistsByText("Do you have a clinic code?", 20000, false)) {
            i("Screen enter clinic code is displayed");
            testCase.addExpectedResult(new ExpectedResult("Pin is created. New user created successfully message will display. Enter PIN window is displayed.", true));
        } else  {
            i("Screen enter clinic code is not displayed");
            testCase.addExpectedResult(new ExpectedResult("Pin is created. New user created successfully message will display. Enter PIN window is displayed.", false));
        }

        i("press home button");
        testHelper.pressHome();

        if(testHelper.waitUntilGoneByText("Do you have a clinic code?", 20000, false)) {
            i("Entrada app is closed");
            testCase.addExpectedResult(new ExpectedResult("Entrada app is closed. PIN has been created.", true));
        } else  {
            i("Entrada app is not closed");
            testCase.addExpectedResult(new ExpectedResult("Entrada app is closed. PIN has been created.", false));
        }

        testCases.add(testCase);
    }

    public void tc265() {
        i(Description.TC265);
        Constant.Temp.TEST_ID = "TC265";

        testCase = new TestCase("TC265", "android-entrada_Un-Install_OK");
        testCaseHelper.openAppsScreen();

        ArrayList<View> views = testHelper.getCurrentViews(android.widget.TextView.class);
        if(!testCaseHelper.findEntradaApplication(views, false)) {
            i("Entrada application is not installed.\n" +
                    "installing...");
            testCaseHelper.installEntradaViaAdb();
            i("restart test case tc265");
            if(isFirstLaunch) {
                isFirstLaunch = false;
                tc265();
            } else {
                i("can not install Entrada application");
//                testCases.add(testCase);
                return;
            }
        } else {
            i("Entrada application if found");
            testCaseHelper.uninstallEntrada(testCase);
        }
        i(testCase.toString());
        testCases.add(testCase);
    }

    public void tc122() {
        i(Description.TC122);
        Constant.Temp.TEST_ID = "TC122";

        testCase = new TestCase("TC122", "android_entrada_Install_Install");

        if(!openInstallationScreen(TestNamesEnum.TC122)){
//            testCases.add(testCase);
            return;
        }

        testCaseHelper.waitForTextAndClick("next", false);
        testCaseHelper.waitForTextAndClick("Install", true);
        if(testHelper.waitForExistsByText("installing", 10000, false)) {
            i("installing dialog is appeared");
            testCase.addExpectedResult(new ExpectedResult("Entrada Installing window will be displayed while it installs. App installed displays when completed.", true));
            testCaseHelper.takeScreenShot("installing_dialog_pass");
        } else {
            i("installing dialog did not appear");
            testCase.addExpectedResult(new ExpectedResult("Entrada Installing window will be displayed while it installs. App installed displays when completed.", false));
            testCaseHelper.takeScreenShot("installing_dialog_fail");
            testCases.add(testCase);
            return;
        }


        if(testHelper.waitForExistsByText("App installed", 10000, false)) {
            i("install completed dialog is appeared");
            testCaseHelper.takeScreenShot("install_completed_dialog_pass");
        } else {
            i("install completed dialog did not appear");
            testCaseHelper.takeScreenShot("install__completed_dialog_fail");
            testCases.add(testCase);
            return;
        }

        View done = testHelper.getViewByText("done", false, false);
        done.click();

        testHelper.pressHome();
        if(testCaseHelper.findEntradaApplication(testHelper.getCurrentViews(android.widget.TextView.class), true)){
            i("Entrada application is installed, icon is found on home screen");
            testCase.addExpectedResult(new ExpectedResult("Install is complete. Icon will be placed on the device home screen.", true));
            testCaseHelper.takeScreenShot("entrada_icon_is_found_on_home_screen");
        } else {
            i("Entrada application is installed, icon is not found on home screen");
            testCase.addExpectedResult(new ExpectedResult("Install is complete. Icon will be placed on the device home screen.", false));
            testCaseHelper.takeScreenShot("entrada_icon_is_not_found_on_home_screen");
        }
        testCases.add(testCase);
    }

    public void tc266() {
        i(Description.TC266);
        Constant.Temp.TEST_ID = "TC266";

        testCase = new TestCase("TC266", "android-entrada_Install_Cancel");

        testHelper.pressHome();

        testCaseHelper.openAppsScreen();
        testHelper.sleep(1000);
        ArrayList<View> views = testHelper.getCurrentViews(android.widget.TextView.class);

        if(!openInstallationScreen(TestNamesEnum.TC266)) return;
        if(testHelper.waitForExistsByText("cancel", 10000, false) && testHelper.waitForExistsByText("next", 1, false)){
            testCase.addExpectedResult(new ExpectedResult("Do you want to install this application? IT will get access to: window will be displayed. Cancel and Next button will be displayed.", true));
            i("Cancel and Next buttons displayed");
            testCaseHelper.takeScreenShot("Cancel_and_Next_buttons_displayed");
        } else {
            testCase.addExpectedResult(new ExpectedResult("Do you want to install this application? IT will get access to: window will be displayed. Cancel and Next button will be displayed.", false));
            i("Cancel and Next buttons not displayed");
            testCaseHelper.takeScreenShot("Cancel_and_Next_buttons_not_displayed");
            testCases.add(testCase);
            return;
        }

        View cancel = testHelper.getViewByText("cancel", false);
        cancel.click();
        //todo add logic to check screens before launching of installing dialog and click button to cancel
//        if(!testCaseHelper.isScreenChanged(testHelper.getCurrentViews(android.widget.TextView.class), views)) {
            testCase.addExpectedResult(new ExpectedResult("Install will be cancelled. .apk file will be displayed.", true));
            i("Displayed screen which was before installing");
            testCaseHelper.takeScreenShot("screen_is_not_changed");
//        } else {
//            testCase.addExpectedResult(new ExpectedResult("Install will be cancelled. .apk file will be displayed.", false));
//            i("Displayed screen which different from screen which was before installing");
//            testCaseHelper.takeScreenShot("screen_is__changed");
//        }
        if(!openInstallationScreen(TestNamesEnum.TC266)) {
//            testCases.add(testCase);
            return;
        }
        if(!testCaseHelper.waitForTextAndClick("next", false)){
            i("next button is not found");
            testCaseHelper.takeScreenShot("next button is not found");
            testCases.add(testCase);
            return;
        }

        if(!testCaseHelper.waitForTextAndClick("cancel", false)){
            i("cancel button is not found");
            testCaseHelper.takeScreenShot("cancel button is not found");
            testCases.add(testCase);
            return;
        }

        //todo add logic to check screens before launching of installing dialog and click button to cancel
//        if(!testCaseHelper.isScreenChanged(testHelper.getCurrentViews(android.widget.TextView.class), views)) {
            testCase.addExpectedResult(new ExpectedResult("Install will be cancelled. .apk file will be displayed.", true));
            i("Displayed screen which was before installing");
            testCaseHelper.takeScreenShot("screen_is_not_changed");
//        } else {
//            testCase.addExpectedResult(new ExpectedResult("Install will be cancelled. .apk file will be displayed.", false));
//            i("Displayed screen which different from screen which was before installing");
//            testCaseHelper.takeScreenShot("screen_is_not_changed");
//        }
        testCases.add(testCase);
    }

    private boolean openInstallationScreen(TestNamesEnum testNamesEnum){
        if(!testCaseHelper.openInstallationScreen()) {
            if (isFirstLaunch) {
                isFirstLaunch = false;
                switch (testNamesEnum) {
                    case TC122:
                        tc122();
                        break;
                    case TC265:
                        tc265();
                        break;
                    case TC266:
                        tc266();
                        break;
                }
                return false;
            }
            testCaseHelper.takeScreenShot("testcase_fail");
            i("Can not enable settings to install application from unknown sources");
            return false;
        }
        return true;
    }

    public TestCase getTestCase() {
        return testCase;
    }
}