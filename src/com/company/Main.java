package com.company;

import com.company.app.TestManager;
import com.company.test.Test;
import net.bugs.testhelper.TestHelper;
import com.company.enums.TestNamesEnum;

import java.util.ArrayList;

import static net.bugs.testhelper.helpers.LoggerUtil.i;

public class Main {

    public static void main(String[] args) throws Exception {
        TestManager testManager = TestManager.getInstance(args);
        Test test = new Test();
        test.startTest(testManager.getCommandLine().getTestName());

    }
}
