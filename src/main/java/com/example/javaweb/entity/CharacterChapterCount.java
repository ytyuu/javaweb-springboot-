package com.example.javaweb.entity;

public class CharacterChapterCount {
    private String characterName;
    private Integer appearanceChapters;

    // 无参构造
    public CharacterChapterCount() {}

    // 有参构造
    public CharacterChapterCount(String characterName, Integer appearanceChapters) {
        this.characterName = characterName;
        this.appearanceChapters = appearanceChapters;
    }

    // getter/setter
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
}