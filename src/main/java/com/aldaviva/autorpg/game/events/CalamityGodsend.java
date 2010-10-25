package com.aldaviva.autorpg.game.events;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aldaviva.autorpg.AutoRPGException.NotEnoughPlayersError;
import com.aldaviva.autorpg.data.entities.Character;

public abstract class CalamityGodsend extends RandomEvent {

	private static final Logger LOGGER = LoggerFactory.getLogger(CalamityGodsend.class);
	
	protected static final String expAnnouncement = "${player}.experience ${operator} ${reward}";

	private int reward;
	private Character target;

	protected abstract int getRewardMultiplier();
	protected abstract String getPrefix();
	protected abstract String getOperator();
	protected abstract List<String> getDescriptions();

	protected void occur() {
		try {
			target = Character.findOnlinePlayersByRandom(1).get(0);
	
			int exp = target.getExperience();
			reward = getRewardMultiplier() * getRandomInt((int) 0.02 * exp, (int) 0.05 * exp);
	
			target.setExperience(target.getExperience() + reward);
	
			target.merge();
		
		} catch(NotEnoughPlayersError e) {
			LOGGER.info("Not enough players for this Random Event to occur.");
			//do nothing
		}
	}

	public String getAnnouncement() {
		StringBuilder buf = new StringBuilder()

		.append(getPrefix()).append(": ")

		.append(getDescriptions().get(getRandomInt(0, getDescriptions().size() - 1))).append(". ")

		.append(expAnnouncement).append(".");

		return buf.toString().replace("${player}", target.getName()).replace("${operator}", getOperator())
				.replace("${reward}", String.valueOf(Math.abs(reward)));
	}

}