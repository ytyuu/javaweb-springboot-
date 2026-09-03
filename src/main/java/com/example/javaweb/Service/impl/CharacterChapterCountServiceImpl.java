package com.example.javaweb.Service.impl;

import com.example.javaweb.Service.CharacterChapterCountService;
import com.example.javaweb.entity.CharacterChapterCount;
import com.example.javaweb.mapper.CharacterChapterCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterChapterCountServiceImpl implements CharacterChapterCountService {

    @Autowired
    private CharacterChapterCountMapper characterChapterCountMapper;

    @Override
    public List<CharacterChapterCount> getAllCharacterChapterCounts() {
        return characterChapterCountMapper.findAll();
    }

    @Override
    public List<CharacterChapterCount> getTopNCharacterChapterCounts(int n) {
        return characterChapterCountMapper.findTopN(n);
    }
}