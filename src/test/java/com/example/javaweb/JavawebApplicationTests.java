package com.example.javaweb;

import com.example.javaweb.entity.CharacterAppearance;
import com.example.javaweb.entity.ForceAppearance;
import com.example.javaweb.service.CharacterAppearanceService;
import com.example.javaweb.service.ForceAppearanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = JavawebApplication.class)
class JavawebApplicationTests {

	@Autowired
	private CharacterAppearanceService characterAppearanceService;

	@Autowired
	private ForceAppearanceService forceAppearanceService;

	@Test
	void contextLoads() {
	}

	@Test
	void testCharacterAppearanceService() {
		List<CharacterAppearance> characters = characterAppearanceService.getAllCharacterAppearance();
		assert !characters.isEmpty();
		System.out.println("Character count: " + characters.size());
		for (CharacterAppearance character : characters) {
			System.out.println(character.getCharacterName() + ": " + character.getAppearanceChapters());
		}
	}

	@Test
	void testForceAppearanceService() {
		List<ForceAppearance> forces = forceAppearanceService.getAllForceAppearance();
		assert !forces.isEmpty();
		System.out.println("Force count: " + forces.size());
		for (ForceAppearance force : forces) {
			System.out.println(force.getChapterRange() + " - Wei:" + force.getWeiCount() + ", Shu:" + force.getShuCount() + ", Wu:" + force.getWuCount() + ", Qun:" + force.getQunCount());
		}
	}

}