package com.company.report;

import com.company.model.TestCase;
import net.bugs.testhelper.TestHelper;

import java.util.ArrayList;

/**
 * Created by nikolai on 29.01.2015.
 */
public class Report {

    ArrayList<TestCase> testCases;
    TestHelper testHelper;

    public Report(ArrayList<TestCase> testCases, TestHelper testHelper) {
        this.testCases = testCases;
        this.testHelper = testHelper;
    }

    private String getHeaderHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(HtmlPattern.HTMLCellHeaderPattern, "Index"));
        stringBuilder.append(String.format(HtmlPattern.HTMLCellHeaderPattern, "ID"));
        stringBuilder.append(String.format(HtmlPattern.HTMLCellHeaderPattern, "Title / Expected results"));
        stringBuilder.append(String.format(HtmlPattern.HTMLCellHeaderPattern, "Result"));


        return stringBuilder.toString();
//        stringBuilder.append(String.format(HtmlPattern.HTMLCellHeaderPattern, "Device Id"));
//        stringBuilder.append(String.format(HtmlPattern.HTMLCellHeaderPattern, "Total Time"));
    }

    public String getHtmlReport() {
        String content = getHeaderHeader();

        for (int currentIndex = 0; currentIndex < testCases.size(); currentIndex ++) {
            content = content + testCases.get(currentIndex).getReportRow(currentIndex);
        }

        String header = "<br>Device: " + testHelper.getHwDevice() +
                ";<br>OS: " + testHelper.getOsDevice() +
                ";<br>Device id: " + testHelper.getDeviceID() +
                ";<br>Slave: " + testHelper.getOsFullName() + "<br><br>";

        return String.format(HtmlPattern.HTMLPattern, header, content);
    }
}
