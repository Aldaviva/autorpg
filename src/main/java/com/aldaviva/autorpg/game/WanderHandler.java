package com.aldaviva.autorpg.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.persistence.types.MapPoint;

@Configurable
class WanderHandler implements CharacterProgressHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WanderHandler.class);

	@Autowired
	private RealmMap worldMap;

	public void handleProgress(Character character) {
		MapPoint origin = character.getLocation();
		if (origin == null) {
			origin = new MapPoint();
		}
		MapPoint destination = worldMap.getDestination(origin, RealmMap.MovementDirection.getRandomDirection());
		character.setLocation(destination);
		LOGGER.debug(character.getName() + " has walked to " + destination.x + ", " + destination.y);
	}
}