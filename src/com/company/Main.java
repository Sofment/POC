package com.company;

import net.bugs.imap.GMailSender;
import net.bugs.testhelper.TestHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static net.bugs.testhelper.helpers.LoggerUtil.i;

public class Main {

    public static void main(String[] args) throws Exception {
	// write your code here
        TestHelper testHelper = new TestHelper(args[0]);
//        GMailSender gMailSender = testHelper.getGMailSender("recorderofcalls@gmail.com", "!Softtec08002");
//        gMailSender.setBody("<html><body><br><a href=\"https://nook.testrail.com/index.php?/runs/view/630&group_by=cases:section_id&group_order=asc\">TestRail</a><br>Devices:Nexus 5(03b04015828dfbbf); Nexus 7(09e094f6); <br>Build:bnereader-4.0.0.723-debug.apk<br><h3>Android Client Quicklooks</h3><table border=1><th align=\"center\">Index</th><th align=\"center\">ID</th><th align=\"center\">Title</th><th align=\"center\">Result</th><th align=\"center\">Device Id</th><th align=\"center\">Total Time</th></table><br>Passed:0<br>Failed:0<br>Untested:0<br>Retest:0<br>Other:0</body></html>\n");
//        gMailSender.setSubject("Test subject");
//        gMailSender.setTo("m.sushkevich@agilefusion.com", "n.ivanov@agilefusion.com");
//        gMailSender.addAttachment("rail_content.html");
//        gMailSender.addAttachment("rail_content.html");
//        gMailSender.send();

        testHelper.getSettingsHelper().openSettings();
        testHelper.sleep(1000);
        testHelper.getSettingsHelper().openSoundSettings();
        testHelper.sleep(1000);
        testHelper.getSettingsHelper().openSyncSettings();
        testHelper.sleep(1000);
        testHelper.getSettingsHelper().openUserDictionarySettings();
        testHelper.sleep(1000);
        testHelper.getSettingsHelper().openWifiSettings();
        testHelper.sleep(1000);
        testHelper.getSettingsHelper().openWirelessSettings();
        testHelper.sleep(1000);
    }
}
