package com.company.report;

import com.company.model.TestCase;

import java.util.ArrayList;

/**
 * Created by nikolai on 29.01.2015.
 */
public class Report {

    ArrayList<TestCase> testCases;

    public Report(ArrayList<TestCase> testCases) {
        this.testCases = testCases;
    }

    private String getHeaderHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(HtmlPattern.HTMLCellHeaderPattern, "Index"));
        stringBuilder.append(String.format(HtmlPattern.HTMLCellHeaderPattern, "ID"));
        stringBuilder.append(String.format(HtmlPattern.HTMLCellHeaderPattern, "Title"));
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

        return String.format(HtmlPattern.HTMLPattern, content);
    }
}
