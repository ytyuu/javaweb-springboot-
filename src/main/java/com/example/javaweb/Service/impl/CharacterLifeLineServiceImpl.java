package com.example.javaweb.Service.impl;

import com.example.javaweb.Service.CharacterLifeLineService;
import com.example.javaweb.entity.CharacterLifeLine;
import com.example.javaweb.mapper.CharacterLifeLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterLifeLineServiceImpl implements CharacterLifeLineService {

    @Autowired
    private CharacterLifeLineMapper characterLifeLineMapper;

    @Override
    public List<CharacterLifeLine> getAllCharacterLifeLines() {
        return characterLifeLineMapper.getAllCharacterLifeLines();
    }

    @Override
    public CharacterLifeLine getCharacterLifeLineByName(String name) {
        return characterLifeLineMapper.getCharacterLifeLineByName(name);
    }

    @Override
    public List<CharacterLifeLine> getCharacterLifeLinesByNames(List<String> names) {
        return characterLifeLineMapper.getCharacterLifeLinesByNames(names);
    }
}