package com.example.javaweb.Service;

import com.example.javaweb.entity.Edge;
import java.util.List;

public interface EdgeService {
    List<Edge> findAll();

    List<Edge> findByNodesIn(List<String> nodes);
}