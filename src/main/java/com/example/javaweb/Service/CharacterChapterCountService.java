package com.example.javaweb.Service;

import com.example.javaweb.entity.CharacterChapterCount;
import java.util.List;

public interface CharacterChapterCountService {
    List<CharacterChapterCount> getAllCharacterChapterCounts();

    List<CharacterChapterCount> getTopNCharacterChapterCounts(int n);
}