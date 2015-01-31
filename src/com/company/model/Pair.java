package com.company.model;

/**
 * Created by nikolai on 31.01.2015.
 */
public class Pair {
    private String key;
    private String value;

    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Pair(String[] pairLines) {
        if(pairLines == null || pairLines.length != 2){
            this.key = "";
            this.value = "";
        } else {
            this.key = pairLines[0];
            this.value = pairLines[1];
        }
        this.toString();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
