package com.example.javaweb.service.impl;

import com.example.javaweb.entity.CharacterAppearance;
import com.example.javaweb.mapper.CharacterAppearanceMapper;
import com.example.javaweb.service.CharacterAppearanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterAppearanceServiceImpl implements CharacterAppearanceService {

    @Autowired
    private CharacterAppearanceMapper characterAppearanceMapper;

    @Override
    public List<CharacterAppearance> getAllCharacterAppearance() {
        return characterAppearanceMapper.selectAllCharacterAppearance();
    }
}