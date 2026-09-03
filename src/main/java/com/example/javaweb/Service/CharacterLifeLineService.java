package com.example.javaweb.Service;

import com.example.javaweb.entity.CharacterLifeLine;
import java.util.List;

public interface CharacterLifeLineService {
    /**
     * 获取所有人物生命线数据
     */
    List<CharacterLifeLine> getAllCharacterLifeLines();
    
    /**
     * 根据人物名称获取生命线数据
     */
    CharacterLifeLine getCharacterLifeLineByName(String name);
    
    /**
     * 根据多个姓名获取生命线数据
     */
    List<CharacterLifeLine> getCharacterLifeLinesByNames(List<String> names);
}