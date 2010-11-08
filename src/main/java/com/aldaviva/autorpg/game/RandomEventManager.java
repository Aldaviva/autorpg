package com.aldaviva.autorpg.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aldaviva.autorpg.game.events.FindItemEvent;
import com.aldaviva.autorpg.game.events.HandofGodEvent;
import com.aldaviva.autorpg.game.events.QuestStartEvent;
import com.aldaviva.autorpg.game.events.RandomEvent;

@Component
public class RandomEventManager implements PeriodicUpdater {

	private static final Logger LOGGER = LoggerFactory.getLogger(RandomEventManager.class);

	private List<RandomEvent> randomEvents = new ArrayList<RandomEvent>();

	public void init() {
		LOGGER.debug("Initializing Random Event Manager.");

		randomEvents.add(new HandofGodEvent());
		randomEvents.add(new FindItemEvent());
		randomEvents.add(new QuestStartEvent());
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
