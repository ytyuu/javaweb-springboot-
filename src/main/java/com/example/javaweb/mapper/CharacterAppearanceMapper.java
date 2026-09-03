package com.example.javaweb.mapper;

import com.example.javaweb.entity.CharacterAppearance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CharacterAppearanceMapper {
    // 查询所有人物出场数据
    @Select("SELECT * FROM character_appearance ORDER BY appearance_chapters DESC LIMIT 5")
    List<CharacterAppearance> selectAllCharacterAppearance();
}