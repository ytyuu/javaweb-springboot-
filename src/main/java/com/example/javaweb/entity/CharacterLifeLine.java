package com.example.javaweb.entity;

public class CharacterLifeLine {
    private String name;
    private Integer start;
    private Integer end;
    private Integer lifelong;

    // 无参构造
    public CharacterLifeLine() {}

    // 有参构造
    public CharacterLifeLine(String name, Integer start, Integer end, Integer lifelong) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.lifelong = lifelong;
    }

    // getter/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getLifelong() {
        return lifelong;
    }

    public void setLifelong(Integer lifelong) {
        this.lifelong = lifelong;
    }
}