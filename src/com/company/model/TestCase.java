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
    private int status;
    private List<String> steps = new ArrayList<String>();
    private ArrayList<ExpectedResult> expectedResults = new ArrayList<ExpectedResult>(){};
    private String id;

    public TestCase(String id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        stringBuilder.append(String.format(HtmlPattern.HTMLStyleSingleCellPattern, "align=\"center\"", ++index));
        stringBuilder.append(String.format(HtmlPattern.HTMLStyleSingleCellPattern, "align=\"center\"", this.id));
        stringBuilder.append(String.format(HtmlPattern.HTMLSingleCellPattern, this.name));

        String formatRaw = "";
        boolean result;
        formatRaw = (result = getExpectedResultStatus()) ? HtmlPattern.HTMLSinglePassedCellPattern : HtmlPattern.HTMLSingleFailedCellPattern;

        stringBuilder.append(String.format(formatRaw, result));

        String line = String.format(HtmlPattern.HTMLSingleRawPattern, stringBuilder.toString());

        for (int currentIndex = 0; currentIndex < expectedResults.size(); currentIndex ++) {
            stringBuilder = new StringBuilder();
            if(currentIndex == 0)
                stringBuilder.append(String.format(HtmlPattern.HTMLStyleSingleCellPattern, "rowspan=\"" + expectedResults.size() + "\" align=\"center\"", ""));
            stringBuilder.append(String.format(HtmlPattern.HTMLStyleSingleCellPattern, " colspan=\"2\"", expectedResults.get(currentIndex).getDescription()));
            formatRaw = (result = expectedResults.get(currentIndex).getResult()) ? HtmlPattern.HTMLSinglePassedCellPattern : HtmlPattern.HTMLSingleFailedCellPattern;

            stringBuilder.append(String.format(formatRaw, result));
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
                ", \"status\":" + status +
                "," + result + "}}";
    }
}