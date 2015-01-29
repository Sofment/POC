package com.company;

import com.company.app.TestManager;
import com.company.test.Test;

public class Main {

    public static void main(String[] args) throws Exception {
        TestManager testManager = TestManager.getInstance(args);
        Test test = new Test();
        test.startTest(testManager.getCommandLine().getTestName());
    }
}
