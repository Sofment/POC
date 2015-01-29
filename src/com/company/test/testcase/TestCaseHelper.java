package com.company.test.testcase;

import net.bugs.testhelper.TestHelper;
import net.bugs.testhelper.view.View;

import java.util.ArrayList;

import static net.bugs.testhelper.helpers.LoggerUtil.i;

/**
 * Created by avsupport on 1/29/15.
 */
public class TestCaseHelper {

    private TestHelper testHelper;
    private String screenShotFolder = "screenshots";

    public TestCaseHelper(TestHelper testHelper) {
        this.testHelper = testHelper;
        this.screenShotFolder = testHelper.propertiesManager.getProperty("PATH_TOP_SCREEN_SHOT_FOLDER");
    }

    public void uninstallEntrada() {
        testHelper.getSettingsHelper().openApplicationDetails("com.entradahealth.entrada.android");
        if(!testHelper.waitForExistsByText("Uninstall", 10000, false)) {
            i("uninstall button is not found");
            takeScreenShot(System.currentTimeMillis() + "_" +
                    "uninstall_button_is_not_found_fail");
            System.exit(0);
        }
        View uninstallButton = testHelper.getViewByText("Uninstall", false, false);
        i("click on uninstall button");
        uninstallButton.click();

        if(testHelper.waitForExistsByText("Cancel", 10000, false)
                && testHelper.waitForExistsByText("ok", 1, false)){
            i("Uninstall dialog appeared");
            takeScreenShot(System.currentTimeMillis() + "_" +
                    "uninstall_dialog_appeared_pass");
        } else {
            i("Uninstall dialog did not appear");
            takeScreenShot(System.currentTimeMillis() + "_" +
                    "uninstall_dialog_did_not_appear_fail");
            System.exit(0);
        }
        View okButton = testHelper.getViewByText("ok", false);
        okButton.click();
        testHelper.sleep(5000);
        ArrayList<View> views = testHelper.getCurrentViews(android.widget.TextView.class);
        if(findEntradaApplication(views)) {
            i("Entrada application is not uninstalled");
            takeScreenShot(System.currentTimeMillis() + "_" +
                    "entrada_is_not_uninstalled_fail");
        } else {
            i("Entrada application is uninstalled");
            takeScreenShot(System.currentTimeMillis() + "_" +
                    "entrada_is_uninstalled_pass");
        }
    }

    public boolean findEntradaApplication(ArrayList<View> textViews) {
        if(checkEntradaIcon(textViews)) return true;

        takeScreenShot(System.currentTimeMillis() + "_" +
                "application list");
        int height = testHelper.getScreenHeight();
        int width = testHelper.getScreenWidth();
        ArrayList<View> views;

        while (true) {
            testHelper.swipe(10, height / 2, width - 10, height / 2);
            views = testHelper.getCurrentViews(android.widget.TextView.class);
            takeScreenShot(System.currentTimeMillis() + "_" +
                    "application list");
            if(!changeScreen(textViews, views)) break;
            textViews.clear();
            textViews.addAll(views);
            if(checkEntradaIcon(views)) return true;
        }
        while (true) {
            testHelper.swipe(width - 10, height / 2, 10, height / 2);
            views = testHelper.getCurrentViews(android.widget.TextView.class);
            takeScreenShot(System.currentTimeMillis() + "_" +
                    "application list");
            if(!changeScreen(textViews, views)) break;
            textViews.clear();
            textViews.addAll(views);
            if(checkEntradaIcon(views)) return true;
        }
        return false;
    }

    public boolean changeScreen(ArrayList<View> textViews, ArrayList<View> views) {
        if(textViews.size() != views.size()) return true;
        for(int currentIndex = 0; currentIndex < textViews.size(); currentIndex ++) {
            if(!textViews.get(currentIndex).getText().equals(views.get(currentIndex).getText())) return true;
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

    public void takeScreenShot(String name) {
        testHelper.takeScreenshot(name, screenShotFolder);
    }

    public void enableInstallationFromUnknownSources() {
        View settingsButton = testHelper.getViewByText("Settings", false);
        settingsButton.click();
        if(!testHelper.waitForExistsByText("Security", 10000, true)) {
            takeScreenShot("text_Security_is_not_found");
            i("text Security is not found");
            System.exit(0);
        }
        View unknownSources = findUnKnownSources();
        if(unknownSources != null && unknownSources.exists()) {
            i("click on unknown sources");
            unknownSources.click();
            if(!testHelper.waitForExistsByText("cancel", 10000, false)) {
                takeScreenShot("unknown_sources_dialog");
                System.exit(0);
            }
            View ok = testHelper.getViewByText("OK", false, false);
            if(!ok.exists()) {
                takeScreenShot("unknown_sources_dialog");
                System.exit(0);
            }
            ok.click();
            testHelper.pressBack();
        }
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
            if(!changeScreen(newTextViews, textViews)) break;
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
            if(!changeScreen(newTextViews, textViews)) break;
            textViews.clear();
            textViews.addAll(newTextViews);
            unknownSources = testHelper.getViewByText("Unknown sources");
            if(unknownSources.exists()) return unknownSources;
        }

        return null;
    }

    public void waitTextAndClick(String text, boolean isFullMatch) {
        if(!testHelper.waitForExistsByText(text, 10000, isFullMatch)) {
            i(text + " does not exist");
            takeScreenShot(text + "_is_not_found");
            System.exit(0);
        }
        View next = testHelper.getViewByText(text, isFullMatch, false);
        i("click on text: " + text);
        next.click();
    }
}
