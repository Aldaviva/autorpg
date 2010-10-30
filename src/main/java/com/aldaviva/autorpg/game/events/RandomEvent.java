package com.aldaviva.autorpg.game.events;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.display.Bulletin;
import com.aldaviva.autorpg.display.BulletinManager;
import com.aldaviva.autorpg.game.GameState;

@Configurable
public abstract class RandomEvent {

	private static final int SECONDS_PER_DAY = 86400;
	
	@Autowired
	protected BulletinManager bulletinManager;
	
	private static Random prng = new Random();

	public abstract int getTimesPerDay();

	protected abstract void occur();

	public abstract String getAnnouncement();

	public void rollToOccur() {
		int rollsPerDay = SECONDS_PER_DAY / GameState.getTickInterval();
		if (getRandomInt(1, rollsPerDay) <= getTimesPerDay()) {
			occur();
			bulletinManager.publish(new Bulletin(getAnnouncement()));
		}
	}
	
	public final void forceOccur(){
		occur();
		bulletinManager.publish(new Bulletin(getAnnouncement()));
	}

	public static int getRandomInt(int lowerInclusive, int upperInclusive) {
		if (upperInclusive < lowerInclusive) {
			throw new IllegalArgumentException("Arguments in wrong order");
		}

		int range = upperInclusive - lowerInclusive + 1; // both side are inclusive
		int random = prng.nextInt(range);
		random += lowerInclusive;
		return random;
	}

}