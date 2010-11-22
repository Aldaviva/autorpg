package com.aldaviva.autorpg.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.display.bulletin.Bulletin;
import com.aldaviva.autorpg.display.bulletin.BulletinManager;
import com.aldaviva.autorpg.display.bulletin.Message;

@Configurable
public class ExperienceHandler implements CharacterProgressHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceHandler.class);
	
	private static final int EXPERIENCE_PER_SECOND = 1;

	@Autowired
	private BulletinManager bulletinManager;
	
	public void handleProgress(Character character) {
		Integer experience = character.getExperience();
		experience += EXPERIENCE_PER_SECOND * GameState.getTickInterval();

		character.setExperience(experience);

		LOGGER.debug(character.getName() + " has " + character.getExperience() + " experience.");

		int level = character.calculateLevelFromExperience();
		if (level != character.getLevel()) {
			character.setLevel(level);
			onLevelUp(character);
		}
	}
	
	private void onLevelUp(Character character){
		Bulletin bulletin = new Bulletin(new Message.CharacterLevelsUp(character));
		bulletinManager.publish(bulletin);
	}
	
	

}