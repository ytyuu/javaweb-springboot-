package com.example.javaweb.mapper;

import com.example.javaweb.entity.ForceAppearance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ForceAppearanceMapper {
    // 查询所有势力出场数据
    @Select("SELECT * FROM force_appearance")
    List<ForceAppearance> selectAllForceAppearance();
}