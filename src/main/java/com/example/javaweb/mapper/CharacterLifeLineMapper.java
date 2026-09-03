package com.example.javaweb.mapper;

import com.example.javaweb.entity.CharacterLifeLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CharacterLifeLineMapper {
    //获取所有人物生命线数据
    List<CharacterLifeLine> getAllCharacterLifeLines();
    
    //根据人物名称获取生命线数据
    CharacterLifeLine getCharacterLifeLineByName(@Param("name") String name);
    
    //根据多个姓名获取生命线数据
    List<CharacterLifeLine> getCharacterLifeLinesByNames(@Param("names") List<String> names);
}