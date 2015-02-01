package com.company.test.testcase;

import com.company.app.TestManager;
import com.company.enums.ConfigParameter;
import com.company.model.TestCase;
import com.company.test.Test;
import com.company.utils.Constant;
import net.bugs.testhelper.TestHelper;
import net.bugs.testhelper.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.bugs.testhelper.helpers.LoggerUtil.i;

/**
 * Created by avsupport on 1/29/15.
 */
public class TestCaseHelper {

    private TestHelper testHelper;
    public String pin = "0000";

    public TestCaseHelper(TestHelper testHelper) {
        this.testHelper = testHelper;
        this.pin = testHelper.propertiesManager.getProperty(ConfigParameter.PIN.name());
    }

    public boolean uninstallEntrada(TestCase testCase) {
        testHelper.getSettingsHelper().openApplicationDetails("com.entradahealth.entrada.android");
        if(!testHelper.waitForExistsByText("Uninstall", 10000, false)) {
            i("uninstall button is not found");
            takeScreenShot("uninstall_button_is_not_found_fail");
            return false;
        }
        View uninstallButton = testHelper.getViewByText("Uninstall", false, false);
        i("click on uninstall button");
        uninstallButton.click();

        if(testHelper.waitForExistsByText("Cancel", 10000, false)
                && testHelper.waitForExistsByText("ok", 1, false)){
            i("Uninstall dialog appeared");
            testCase.addExpectedResult(new ExpectedResult("Do you want to uninstall this app? Cancel and OK buttons are displayed.", true));
            takeScreenShot("uninstall_dialog_appeared_pass");
        } else {
            i("Uninstall dialog did not appear");
            takeScreenShot("uninstall_dialog_did_not_appear_fail");
            testCase.addExpectedResult(new ExpectedResult("Do you want to uninstall this app? Cancel and OK buttons are displayed.", false));

            return false;
        }
        View okButton = testHelper.getViewByText("ok", false);
        okButton.click();
        testHelper.sleep(5000);
        ArrayList<View> views = testHelper.getCurrentViews(android.widget.TextView.class);
        if(findEntradaApplication(views, false)) {
            testCase.addExpectedResult(new ExpectedResult("Application will display un-installing while it is removing the application and all data. Once deleted, Entrada icon is removed from the applications list", false));

            i("Entrada application is not uninstalled");
            takeScreenShot("entrada_is_not_uninstalled_fail");
        } else {
            i("Entrada application is uninstalled");
            testCase.addExpectedResult(new ExpectedResult("Application will display un-installing while it is removing the application and all data. Once deleted, Entrada icon is removed from the applications list", true));
            takeScreenShot("entrada_is_uninstalled_pass");
        }
        return true;
    }

    public boolean findEntradaApplication(ArrayList<View> textViews, boolean isHomeScreen) {
        if(checkEntradaIcon(textViews)) return true;

        String name = isHomeScreen ? "home screen" : "application list";
        takeScreenShot(name);
        int height = testHelper.getScreenHeight();
        int width = testHelper.getScreenWidth();
        ArrayList<View> views;

        while (true) {
            testHelper.swipe(10, height / 2, width - 10, height / 2);
            views = testHelper.getCurrentViews(android.widget.TextView.class);
            if(!isScreenChanged(textViews, views)) break;
            takeScreenShot(name);
            textViews.clear();
            textViews.addAll(views);
            if(checkEntradaIcon(views)) return true;
        }
        while (true) {
            testHelper.swipe(width - 10, height / 2, 10, height / 2);
            views = testHelper.getCurrentViews(android.widget.TextView.class);
            if(!isScreenChanged(textViews, views)) break;
            takeScreenShot(name);
            textViews.clear();
            textViews.addAll(views);
            if(checkEntradaIcon(views)) return true;
        }
        return false;
    }

    public boolean isScreenChanged(ArrayList<View> textViews, ArrayList<View> views) {
        if(textViews.size() != views.size()) return true;
        for(int currentIndex = 0; currentIndex < textViews.size(); currentIndex ++) {
//            i(textViews.get(currentIndex).getText() + " | " + views.get(currentIndex).getText());
//            i("equals? " + (textViews.get(currentIndex).getText().equals(views.get(currentIndex).getText())));
            if(!(textViews.get(currentIndex).getText().equals(views.get(currentIndex).getText()))) return true;
        }
        return false;
    }

    private boolean checkEntradaIcon(ArrayList<View> textViews) {
        for(View textView : textViews) {
            if(textView.getText().equalsIgnoreCase("entrada"))
                return true;
        }
        return false;
    }

    public String getFormatDate(String format) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat;
        String formattingDate = "";
        if(format == null){
            simpleDateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);
            formattingDate = simpleDateFormat.format(date);
        }else {
            simpleDateFormat = new SimpleDateFormat(format);
            formattingDate = simpleDateFormat.format(date);
        }
        return formattingDate;
    }

    public void takeScreenShot(String name) {
        name = getFormatDate(null) + "_" + Constant.Temp.TEST_ID + "_" + name;
        testHelper.takeScreenshot(name.replaceAll(" ", "_"), TestManager.ScreenShotFolder);
    }

    public boolean enableInstallationFromUnknownSources() {
        View settingsButton = testHelper.getViewByText("Settings", false);
        settingsButton.click();
        if(!testHelper.waitForExistsByText("Security", 10000, true)) {
            takeScreenShot("text_Security_is_not_found");
            i("text Security is not found");
            return false;
        }
        View unknownSources = findUnKnownSources();
        if(unknownSources != null && unknownSources.exists()) {
            i("click on unknown sources");
            unknownSources.click();
            if(!testHelper.waitForExistsByText("cancel", 10000, false)) {
                takeScreenShot("unknown_sources_dialog");
                return false;
            }
            View ok = testHelper.getViewByText("OK", false, false);
            if(!ok.exists()) {
                takeScreenShot("unknown_sources_dialog");
                return false;
            }
            ok.click();
            testHelper.pressBack();
        }
        return true;
    }

    private View findUnKnownSources() {
        int height = testHelper.getScreenHeight();
        int width = testHelper.getScreenWidth();

        View unknownSources = testHelper.getViewByText("Unknown sources");
        if(unknownSources.exists()) return unknownSources;

        ArrayList<View> textViews = testHelper.getCurrentViews(android.widget.TextView.class.getName(), false, false);
        ArrayList<View> newTextViews;
        while(true) {
            testHelper.swipe(width/2, height/2 + height/5, width/2, height/2 - height/5);
            newTextViews = testHelper.getCurrentViews(android.widget.TextView.class);
            if(!isScreenChanged(newTextViews, textViews)) break;
            textViews.clear();
            textViews.addAll(newTextViews);
            unknownSources = testHelper.getViewByText("Unknown sources");
            if(unknownSources.exists()) return unknownSources;
        }

        textViews.clear();
        textViews.addAll(newTextViews);

        while(true) {
            testHelper.swipe(width/2, height/2 - height/5, width/2, height/2 + height/5);
            newTextViews = testHelper.getCurrentViews(android.widget.TextView.class);
            if(!isScreenChanged(newTextViews, textViews)) break;
            textViews.clear();
            textViews.addAll(newTextViews);
            unknownSources = testHelper.getViewByText("Unknown sources");
            if(unknownSources.exists()) return unknownSources;
        }

        return null;
    }

    public boolean waitForTextAndClick(String text, boolean isFullMatch) {
        if(!testHelper.waitForExistsByText(text, 10000, isFullMatch)) {
            i(text + " does not exist");
            takeScreenShot(text + "_is_not_found");
            return false;
        }
        View next = testHelper.getViewByText(text, isFullMatch, false);
        i("click on text: " + text);
        next.click();
        return true;
    }

    public boolean openInstallationScreen() {
        if(isEntradaInstalled()) {
            i("uninstall application");
            testHelper.getAdb().uninstall(Constant.PACKAGE_APP);
        }
        testHelper.getAdb().push("entrada-5.3.32-aligned.apk", "/sdcard/");
        testHelper.getSettingsHelper().openInstallationWindow("/sdcard/entrada-5.3.32-aligned.apk");
        if(testHelper.waitForExistsByText("Settings", 3000, false)) {
            enableInstallationFromUnknownSources();
            return false;
        }
        return true;
    }

    public boolean openAppsScreen() {
        if(!testHelper.waitForExistsByDescriptor("Apps", 5000)) {
            testHelper.pressHome();
        } else {
            List<View> list = testHelper.getCurrentViewsByDescriptor("Apps", false);
            View appsButton = list.get(list.size()-1);
            i("click on Apps button");
            appsButton.click();
            return true;
        }
        if(!testHelper.waitForExistsByDescriptor("Apps", 10000)) {
            i("Apps button is not found");
            return false;
        }

        List<View> list = testHelper.getCurrentViewsByDescriptor("Apps", false);
        View appsButton = list.get(list.size() - 1);
        i("click on Apps button");
        appsButton.click();
        return true;
    }

    public void installEntradaViaAdb() {
        testHelper.getAdb().install("entrada-5.3.32-aligned.apk");
    }

    public void uninstallEntradaViaAbd() {
        testHelper.getAdb().uninstall(Constant.PACKAGE_APP);
    }

    public boolean isEntradaInstalled() {
        ArrayList<String> packages = testHelper.getAdb().getShell().getPackagesOfInstalledApplications();
        return packages.contains(Constant.PACKAGE_APP);
//        return Runner.runProcess(new String[]{"adb", "shell", "pm", "list", "packages"}, Constant.PACKAGE_APP);
    }

    public boolean reinstallEntradaViaAdb() {
        i("reinstallEntradaViaAdb");
        if(isEntradaInstalled()) {
            i("uninstalling Entrada application");
            uninstallEntradaViaAbd();
            if(!isEntradaInstalled()) {
                i("Entrada application has been uninstalled");
            } else {
                i("Entrada application still not uninstalled");
                return false;
            }
        }

        i("installing Entrada application");
        installEntradaViaAdb();
        if(isEntradaInstalled()) {
            i("Entrada application has been installed");
        } else {
            i("Entrada application still not installed");
            return false;
        }
        return true;
    }

    public void startEntrada() {
        i("Start Entrada application");
        testHelper.getAdb().getShell().startIntent(Constant.ENTRADA_MAiN_ACTIVITY);
    }
}
