package com.company.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikolai on 29.01.2015.
 */
public class TestCase {
    private String name;
    private int status;
    private List<String> steps = new ArrayList<String>();

    public TestCase(String name, int status) {
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

    @Override
    public String toString() {
        return "TestCase{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}