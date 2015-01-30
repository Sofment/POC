package com.company.test.testcase;

/**
 * Created by avsupport on 1/30/15.
 */
public class ExpectedResult {
    private String description;
    private boolean result;
    public ExpectedResult(String description, boolean result) {
        this.description = description.replaceAll("\n", " ");
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public boolean getResult() {
        return result;
    }
}
