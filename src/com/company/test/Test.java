package com.company.test;

import com.company.app.TestManager;
import com.company.model.Device;
import com.company.utils.Constant;
import com.company.utils.Runner;
import net.bugs.testhelper.TestHelper;

/**
 * Created by nikolai on 29.01.2015.
 */
public class Test {
    private TestHelper testHelper;
    private TestManager testManager;
    private Device device;

    public Test() {
        testManager = TestManager.getInstance();
        testHelper = testManager.getTestHelper();
        device = testManager.getDevice();
    }

    public void tc265() {

    }

    public void tc122() {
        if(Runner.isInstalledApk(Constant.PACKAGE_APP, device.getId())){
            
        }
    }

}
