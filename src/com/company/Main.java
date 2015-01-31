package com.company;

import com.company.app.TestManager;
import com.company.test.Test;
import com.company.utils.CommandLine;

public class Main {

    public static void main(String[] args) throws Exception {
        CommandLine commandLine = new CommandLine(args);
        if(!commandLine.isSuccessfulParameters()){
            return;
        }

        TestManager testManager = TestManager.getInstance(commandLine);
        testManager.clearFolderWithResults();

        Test test = new Test();
        test.startTest(commandLine.getTestName());
    }
}
