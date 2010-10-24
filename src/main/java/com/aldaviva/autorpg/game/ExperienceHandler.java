package com.aldaviva.autorpg.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;

class ExperienceHandler implements CharacterProgressHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceHandler.class);
	
	public void handleProgress(Character character) {
		Integer experience = character.getExperience();
		experience += ProgressUpdater.EXPERIENCE_PER_SECOND * GameState.getTickInterval();

		character.setExperience(experience);

		LOGGER.info(character.getName() + " has " + character.getExperience() + " experience.");

		int level = calculateLevelFromExperience(character);
		if (level != character.getLevel()) {
			character.setLevel(level);
			onLevelUp(character);
		}
	}
	
	private void onLevelUp(Character character){
		LOGGER.info(character.getName() + " has reached level " + character.getLevel() + "!");
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