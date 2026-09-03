package com.example.javaweb.Service.impl;

import com.example.javaweb.Service.EdgeService;
import com.example.javaweb.entity.Edge;
import com.example.javaweb.mapper.EdgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EdgeServiceImpl implements EdgeService {

    @Autowired
    private EdgeMapper edgeMapper;

    @Override
    public List<Edge> findAll() {
        return edgeMapper.findAll();
    }

    @Override
    public List<Edge> findByNodesIn(List<String> nodes) {
        return edgeMapper.findByNodesIn(nodes);
    }
}