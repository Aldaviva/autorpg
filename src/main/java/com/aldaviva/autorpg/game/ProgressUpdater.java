package com.aldaviva.autorpg.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Player;

@Component
public class ProgressUpdater {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProgressUpdater.class);
	
	
	@Autowired
	private RealmMap worldMap;
	
	static final int EXPERIENCE_PER_SECOND = 1;

	private final List<CharacterProgressHandler> avatarProgressHandlers = new ArrayList<CharacterProgressHandler>();
	
	public void init(){
		LOGGER.debug("Initializing Progress Updater.");
		
		avatarProgressHandlers.add(new ExperienceHandler());
		avatarProgressHandlers.add(new WanderHandler(worldMap));
	}

	public void update() {
		for(Player player : Player.findByOnline()){
		
			for (Character character : Character.findCharactersByPlayer(player).getResultList()) {
	
				for (CharacterProgressHandler handler : avatarProgressHandlers) {
					handler.handleProgress(character);
				}
	
				character.merge();
			}
		
	}
	}

}
