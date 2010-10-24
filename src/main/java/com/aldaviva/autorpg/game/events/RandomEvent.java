package com.aldaviva.autorpg.game.events;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldaviva.autorpg.game.GameState;

public abstract class RandomEvent {

	private static final Logger LOGGER = LoggerFactory.getLogger(RandomEvent.class);
	private static final int SECONDS_PER_DAY = 86400;
	
	private Random prng = new Random();

	public abstract int getTimesPerDay();

	protected abstract void occur();

	public abstract String getAnnouncement();

	public void rollToOccur() {
		int rollsPerDay = SECONDS_PER_DAY / GameState.getTickInterval();
		if (getRandomInt(1, rollsPerDay) <= getTimesPerDay()) {
			occur();
			LOGGER.info(getAnnouncement());
		}
	}
	
	public final void forceOccur(){
		occur();
		LOGGER.info(getAnnouncement());
	}

	protected int getRandomInt(int lowerInclusive, int upperInclusive) {
		if (upperInclusive < lowerInclusive) {
			throw new IllegalArgumentException("Arguments in wrong order");
		}

		int range = upperInclusive - lowerInclusive + 1; // both side are inclusive
		int random = prng.nextInt(range);
		random += lowerInclusive;
		return random;
	}

}