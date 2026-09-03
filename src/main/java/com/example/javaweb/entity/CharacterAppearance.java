package com.example.javaweb.entity;

public class CharacterAppearance {
    private Integer id;
    private String characterName;  // 人物名
    private Integer appearanceChapters;  // 出场章数
    private String force;  // 所属势力

    // 无参构造
    public CharacterAppearance() {}

    // 有参构造
    public CharacterAppearance(String characterName, Integer appearanceChapters, String force) {
        this.characterName = characterName;
        this.appearanceChapters = appearanceChapters;
        this.force = force;
    }

    // getter/setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Integer getAppearanceChapters() {
        return appearanceChapters;
    }

    public void setAppearanceChapters(Integer appearanceChapters) {
        this.appearanceChapters = appearanceChapters;
    }

    public String getForce() {
        return force;
    }

    public void setForce(String force) {
        this.force = force;
    }
}