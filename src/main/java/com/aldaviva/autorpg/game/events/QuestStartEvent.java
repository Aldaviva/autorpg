package com.aldaviva.autorpg.game.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.enums.ConfigurationKey;
import com.aldaviva.autorpg.game.QuestManager;

@Configurable
public class QuestStartEvent extends RandomEvent {

	@Autowired
	private QuestManager questManager;

	@Override
	public int getTimesPerDay() {
		return Integer.parseInt(Configuration.getValue(ConfigurationKey.QUESTS_PER_DAY));
	}

	@Override
	protected void occur() {
		questManager.start();
	}

	@Override
	public String getAnnouncement() {
		return null;
	}

}
