package com.example.javaweb.mapper;

import com.example.javaweb.entity.CharacterChapterCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CharacterChapterCountMapper {
    //查询所有人物出场章数数据
    List<CharacterChapterCount> findAll();

    //查询出场章数最多的前N个人物
    List<CharacterChapterCount> findTopN(int n);
}