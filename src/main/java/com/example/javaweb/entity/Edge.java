package com.example.javaweb.entity;

public class Edge {
    private Integer id;
    private String src;
    private String dst;
    private Integer weight;

    // 无参构造
    public Edge() {}

    // 有参构造
    public Edge(String src, String dst, Integer weight) {
        this.src = src;
        this.dst = dst;
        this.weight = weight;
    }

    // getter/setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}