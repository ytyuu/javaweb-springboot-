package com.example.javaweb.service.impl;

import com.example.javaweb.entity.ForceAppearance;
import com.example.javaweb.mapper.ForceAppearanceMapper;
import com.example.javaweb.service.ForceAppearanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForceAppearanceServiceImpl implements ForceAppearanceService {

    @Autowired
    private ForceAppearanceMapper forceAppearanceMapper;

    @Override
    public List<ForceAppearance> getAllForceAppearance() {
        return forceAppearanceMapper.selectAllForceAppearance();
    }
}