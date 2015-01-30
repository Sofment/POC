package com.company.test.testcase;

/**
 * Created by avsupport on 1/29/15.
 */
public class Description {
    public static final String TC265 = "\n" +
            "##############################################################################\n" +
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

        public static final String TC122 = "\n" +
                "##############################################################################\n" +
                "TC122 - android_entrada_Install_Install\n" +
                "##############################################################################\n" +
                "\n" +
                "The following test will install a new version of android-entrada to the device\n" +
                "\n" +
                "Test steps\n" +
                "\n" +
                "1. Verify no install of android-entrada is installed on device \n" +
                "2. Access or tap APK file \n" +
                "3. Tap Next button \n" +
                "4. Tap Install button \n" +
                "Result A \n" +
                "5. Tap Done button \n" +
                "Result B\n" +
                "\n" +
                "Expected result\n" +
                "\n" +
                "A. Entrada Installing window will be displayed while it installs. App installed displays when completed. \n" +
                "B. Install is complete. Icon will be placed on the device home screen."
                + "\n##############################################################################\n";

        public static final String TC266 = "\n" +
                "##############################################################################\n" +
                "TC266 - android-entrada_Install_Cancel\n" +
                "##############################################################################\n" +
                "\n" +
                "The following test will verify the ability to cancel the install of the Entrada android application at various cancel points during it.\n" +
                "\n" +
                "Test steps\n" +
                "\n" +
                "1. Verify no install of android-entrada is installed on device \n" +
                "2. Tap the .apk file \n" +
                "Result A \n" +
                "3. Click the Cancel button \n" +
                "Result B \n" +
                "4. Tap the .apk file \n" +
                "5. Tap the Next button \n" +
                "6. Tap the Cancel button \n" +
                "Result C\n" +
                "\n" +
                "Expected result\n" +
                "\n" +
                "A. Do you want to install this application? IT will get access to: window will be displayed. Cancel and Next button will be displayed. \n" +
                "B. Install will be cancelled. .apk file will be displayed. \n" +
                "C. Install will be cancelled. .apk file will be displayed"
                + "\n##############################################################################\n";

        public static final String TC267 = "\n" +
                "##############################################################################\n" +
                "TC267 - android-entrada_Install_Create PIN\n" +
                "##############################################################################\n" +
                "\n" +
                "The following test will create a PIN code to use when you login to the application. Test assumes Entrada is already installed on the device and there are no PINS created.\n" +
                "\n" +
                "Test steps\n" +
                "\n" +
                "1. Tap Entrada icon \n" +
                "Result A \n" +
                "2. Tap into Choose PIN \n" +
                "3. Enter valid PIN \n" +
                "4. Tap arrow \n" +
                "Result B \n" +
                "5. Tap into Confirm PIN \n" +
                "6. Enter same valid PIN as in #3 \n" +
                "4. Tap arrow \n" +
                "Result C \n" +
                "5. Tap Device home button \n" +
                "Result D\n" +
                "\n" +
                "Expected result\n" +
                "\n" +
                "A. Choose PIN window is displayed \n" +
                "B. Confirm PIN window is displayed \n" +
                "C. Pin is created. New user created successfully message will display. Enter PIN window is displayed. \n" +
                "D. Entrada app is closed. PIN has been created"
                + "\n##############################################################################\n";
}
