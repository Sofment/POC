package com.company;

import com.company.app.TestManager;
import com.company.test.Test;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        TestManager testManager = TestManager.getInstance(args);

        File file = new File(testManager.getConfigManager().getProperty("PATH_TOP_SCREEN_SHOT_FOLDER"));
        if(file.exists()) {
            if(file.listFiles() != null) {
                for (File childFile : file.listFiles()) {
                    if (childFile.exists()) childFile.delete();
                }
            }
            file.delete();
        }
        Test test = new Test();
        test.startTest(testManager.getCommandLine().getTestName());
    }
}
