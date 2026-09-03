package com.example.javaweb.entity;

public class ForceAppearance {
    private Integer id;
    private String chapterRange;  // 章数区间
    private Integer weiCount;     // 魏出场次数
    private Integer shuCount;     // 蜀出场次数
    private Integer wuCount;      // 吴出场次数
    private Integer qunCount;     // 群雄出场次数

    // 无参构造
    public ForceAppearance() {}

    // getter/setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChapterRange() {
        return chapterRange;
    }

    public void setChapterRange(String chapterRange) {
        this.chapterRange = chapterRange;
    }

    public Integer getWeiCount() {
        return weiCount;
    }

    public void setWeiCount(Integer weiCount) {
        this.weiCount = weiCount;
    }

    public Integer getShuCount() {
        return shuCount;
    }

    public void setShuCount(Integer shuCount) {
        this.shuCount = shuCount;
    }

    public Integer getWuCount() {
        return wuCount;
    }

    public void setWuCount(Integer wuCount) {
        this.wuCount = wuCount;
    }

    public Integer getQunCount() {
        return qunCount;
    }

    public void setQunCount(Integer qunCount) {
        this.qunCount = qunCount;
    }
}