package com.aldaviva.autorpg.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.bulletin.Bulletin;
import com.aldaviva.autorpg.display.bulletin.BulletinManager;

@Configurable
class ExperienceHandler implements CharacterProgressHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceHandler.class);
	
	private static final int EXPERIENCE_PER_SECOND = 1;

	@Autowired
	private BulletinManager bulletinManager;
	
	public void handleProgress(Character character) {
		Integer experience = character.getExperience();
		experience += EXPERIENCE_PER_SECOND * GameState.getTickInterval();

		character.setExperience(experience);

		LOGGER.debug(character.getName() + " has " + character.getExperience() + " experience.");

		int level = calculateLevelFromExperience(character);
		if (level != character.getLevel()) {
			character.setLevel(level);
			onLevelUp(character);
		}
	}
	
	private void onLevelUp(Character character){
		Bulletin bulletin = new Bulletin(character.getName() + " has reached Level " + character.getLevel() + "!");
		bulletinManager.publish(bulletin);
	}
	
	/**
	 * This may be different from getExperience() if a level was just gained
	 * You can never lose a level
	 */
	public int calculateLevelFromExperience(Character character) {
		int levelCap = Integer.parseInt(Configuration.getValue(ConfigurationKey.LEVEL_CAP));
		int secondsToLevelCap = Integer.parseInt(Configuration.getValue(ConfigurationKey.SECONDS_TO_LEVEL_CAP));
		double power = Double.parseDouble(Configuration.getValue(ConfigurationKey.LEVEL_CURVE));
		
		int currentLevel = character.getLevel();
		int currentExperience = character.getExperience();
		
		double mult = levelCap/Math.pow(secondsToLevelCap, power);
		return Math.max(currentLevel, 1 + (int) Math.floor(mult * Math.pow(currentExperience, power)));
	}

}