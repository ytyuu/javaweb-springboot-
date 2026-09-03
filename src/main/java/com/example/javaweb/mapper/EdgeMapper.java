package com.example.javaweb.mapper;

import com.example.javaweb.entity.Edge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EdgeMapper {
    //查询所有边数据
    List<Edge> findAll();

    //根据源节点和目标节点查询边
    List<Edge> findBySrcAndDst(@Param("src") String src, @Param("dst") String dst);

    //查询指定人物之间的边
    List<Edge> findByNodesIn(@Param("nodes") List<String> nodes);
}