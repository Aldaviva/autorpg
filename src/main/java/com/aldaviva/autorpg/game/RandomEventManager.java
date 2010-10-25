package com.aldaviva.autorpg.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aldaviva.autorpg.game.events.Calamity;
import com.aldaviva.autorpg.game.events.FindItem;
import com.aldaviva.autorpg.game.events.GodSend;
import com.aldaviva.autorpg.game.events.RandomEvent;

@Component
public class RandomEventManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(RandomEventManager.class);

	private List<RandomEvent> randomEvents = new ArrayList<RandomEvent>();

	@Autowired
	private CharacterItemManager characterItemManager;
	
	public void init() {
		LOGGER.info("Initializing Random Event Manager.");

		randomEvents.add(new Calamity());
		randomEvents.add(new GodSend());
		randomEvents.add(new FindItem(characterItemManager));
	}
	
	public void force(int i){
		try {
			randomEvents.get(i).forceOccur();
		} catch(IndexOutOfBoundsException e){
			LOGGER.error("Random event #"+i+" not found. Choose a number between 0 and "+(randomEvents.size()-1)+" (inclusive).");
		}
	}

	public void update() {
		for (RandomEvent event : randomEvents) {
			event.rollToOccur();
		}
	}

}
