package com.company.model;

import com.company.report.HtmlPattern;
import com.company.test.testcase.ExpectedResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolai on 29.01.2015.
 */
public class TestCase {
    private String name;
    private List<String> steps = new ArrayList<String>();
    private ArrayList<ExpectedResult> expectedResults = new ArrayList<ExpectedResult>(){};
    private String id;

    public TestCase(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void addStep(String step) {
        steps.add(step);
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public void addExpectedResult(ExpectedResult expectedResult) {
        this.expectedResults.add(expectedResult);
    }

    public String getReportRow(int index) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(HtmlPattern.HTMLStyleSingleCellPattern, "rowspan=\"" + (expectedResults.size() + 1) + "\" align=\"center\"", ++index));
        stringBuilder.append(String.format(HtmlPattern.HTMLStyleSingleCellPattern, "bgcolor=\"#dedede\" rowspan=\"" + (expectedResults.size() + 1) + "\" align=\"center\"", this.id));
        stringBuilder.append(String.format(HtmlPattern.HTMLStyleSingleCellPattern, "bgcolor=\"#dedede\"", this.name));

        String formatRaw = "";
        boolean result;
        formatRaw = (result = getExpectedResultStatus()) ? HtmlPattern.HTMLSinglePassedCellPattern : HtmlPattern.HTMLSingleFailedCellPattern;

        stringBuilder.append(String.format(formatRaw, (result ? "pass" : "fail")));

        String line = String.format(HtmlPattern.HTMLSingleRawPattern, stringBuilder.toString());

        for (ExpectedResult expectedResult : expectedResults) {
            stringBuilder = new StringBuilder();
//            if(currentIndex == 0)
//                stringBuilder.append(String.format(HtmlPattern.HTMLStyleSingleCellPattern, "rowspan=\"" + expectedResults.size() + "\" align=\"center\"", ""));
            stringBuilder.append(String.format(HtmlPattern.HTMLStyleSingleCellPattern, ""/*, " colspan=\"2\""*/, expectedResult.getDescription()));
            formatRaw = (result = expectedResult.getResult()) ? HtmlPattern.HTMLSinglePassedCellPattern : HtmlPattern.HTMLSingleFailedCellPattern;

            stringBuilder.append(String.format(formatRaw, result ? "pass" : "fail"));
            line = line + String.format(HtmlPattern.HTMLSingleRawPattern, stringBuilder.toString());
        }
        return  line;
    }

    private boolean getExpectedResultStatus() {
        for(ExpectedResult expectedResult : expectedResults) {
            if(!expectedResult.getResult()) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = "\"results\":[";
        for(ExpectedResult expectedResult : expectedResults) {
            result = result + "{\"description\":\"" + expectedResult.getDescription() + "\", \"result\":" + expectedResult.getResult() + "},";
        }
        if(result.endsWith(",")) result = result.substring(0, result.length()-1);
        result = result + "]";

        return "{\"TestCase\":{" +
                "\"id\":\"" + id + "\"" +
                "\"name\":\"" + name + "\"" +
                "," + result + "}}";
    }
}