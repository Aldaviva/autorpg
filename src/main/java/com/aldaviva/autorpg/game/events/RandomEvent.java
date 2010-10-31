package com.aldaviva.autorpg.game.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.Utils;
import com.aldaviva.autorpg.display.bulletin.Bulletin;
import com.aldaviva.autorpg.display.bulletin.BulletinManager;
import com.aldaviva.autorpg.game.GameState;

@Configurable
public abstract class RandomEvent {

	private static final Logger LOGGER = LoggerFactory.getLogger(RandomEvent.class);
	
	
	private static final int SECONDS_PER_DAY = 86400;
	
	@Autowired
	protected BulletinManager bulletinManager;
	
	public abstract int getTimesPerDay();

	protected abstract void occur();

	public abstract String getAnnouncement();

	public void rollToOccur() {
		int rollsPerDay = SECONDS_PER_DAY / GameState.getTickInterval();
		
		int timesPerDay = getTimesPerDay();
		int randomInt = Utils.getRandomInt(1, rollsPerDay);
		
		LOGGER.debug("Rolled a "+randomInt+" out of "+rollsPerDay+". For this event, a roll <= "+timesPerDay+" is required.");
		
		if (randomInt <= timesPerDay) {
			occur();
			bulletinManager.publish(new Bulletin(getAnnouncement()));
		}
	}
	
	public final void forceOccur(){
		occur();
		bulletinManager.publish(new Bulletin(getAnnouncement()));
	}

}