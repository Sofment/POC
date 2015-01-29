package com.company.test.testcase;

/**
 * Created by avsupport on 1/29/15.
 */
public class Description {
    public static final String TC265 = "\n##############################################################################\n" +
            "\tTC265 - android-entrada_Un-Install_OK\n" +
            "##############################################################################\n" +
            "\n" +
            "    The following test will verify you can un-install android-entrada without error. Test assumes a version of android-entrada is already installed. Skip if initial install of product.\n" +
            "    Note: For Gallaxy device testing only- you must go through Settings/More tab at top/Application Manager/Entrada folder/Delete to uninstall the Entrada app. Depress and dragging the Entrada icon for Gallaxy does not uninstall the app for this device.\n" +
            "\n" +
            "            Test steps\n" +
            "\n" +
            "    1. Verify entrada is installed on android device\n" +
            "    2. Depress the Entrada icon on device screen until 'Uninstall' becomes displayed. (If testing Gallaxy see description for this step)\n" +
            "    3 Keeping depressed, drag the the Entrada icon to the Uninstall trash can area (If testing Gallaxy see description for this step)\n" +
            "    Result A\n" +
            "    4. Tap the OK button\n" +
            "    Result B\n" +
            "\n" +
            "    Expected result\n" +
            "\n" +
            "    A. Do you want to uninstall this app? Cancel and OK buttons are displayed.\n" +
            "    B. Application will display un-installing while it is removing the application and all data. Once deleted, Entrada icon is removed from the applications list.\n" +
            "##############################################################################\n";
}
