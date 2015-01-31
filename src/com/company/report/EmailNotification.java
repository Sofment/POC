package com.company.report;

import com.company.model.EmailUser;
import net.bugs.imap.GMailSender;
import net.bugs.testhelper.TestHelper;

import java.io.File;

/**
 * Created by nikolai on 31.01.2015.
 */
public class EmailNotification {
    private TestHelper testHelper;
    private EmailUser defaultEmailUser;
    private GMailSender gMailSender;

    public EmailNotification(TestHelper testHelper, EmailUser emailUser) {
        this.testHelper = testHelper;
        if(emailUser == null) {
            defaultEmailUser = new EmailUser("testsreporter@gmail.com", "_Test_Helper_001");
        }
        gMailSender = testHelper.getGMailSender(emailUser.getEmail(), emailUser.getPassword());
    }

    public void addFileToAttachment(File file){
        try {
            gMailSender.addAttachment(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFolderToAttachment(File file){
        if(file.exists()) {
            if(file.listFiles() != null){
                for (File childFile : file.listFiles()) {
                    addFileToAttachment(childFile);
                }
            }
        }
    }

    public boolean sendEmail(String subject, String body, String[] recipients){
        gMailSender.setBody(body);
        gMailSender.setTo(recipients);
        gMailSender.setSubject(subject);
        try {
            gMailSender.send();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
