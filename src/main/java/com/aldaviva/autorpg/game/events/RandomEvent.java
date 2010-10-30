package com.aldaviva.autorpg.game.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.Utils;
import com.aldaviva.autorpg.display.bulletin.Bulletin;
import com.aldaviva.autorpg.display.bulletin.BulletinManager;
import com.aldaviva.autorpg.game.GameState;

@Configurable
public abstract class RandomEvent {

	private static final int SECONDS_PER_DAY = 86400;
	
	@Autowired
	protected BulletinManager bulletinManager;
	
	public abstract int getTimesPerDay();

	protected abstract void occur();

	public abstract String getAnnouncement();

	public void rollToOccur() {
		int rollsPerDay = SECONDS_PER_DAY / GameState.getTickInterval();
		if (Utils.getRandomInt(1, rollsPerDay) <= getTimesPerDay()) {
			occur();
			bulletinManager.publish(new Bulletin(getAnnouncement()));
		}
	}
	
	public final void forceOccur(){
		occur();
		bulletinManager.publish(new Bulletin(getAnnouncement()));
	}

}