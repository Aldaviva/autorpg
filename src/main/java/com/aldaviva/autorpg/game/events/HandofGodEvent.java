package com.aldaviva.autorpg.game.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.aldaviva.autorpg.AutoRPGException.NotEnoughPlayersError;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.entities.Handofgod;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;

public class HandofGodEvent extends RandomEvent {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandofGodEvent.class);
	
	protected static final String expAnnouncement = "${player}.experience ${operator} ${reward}";

	private Character target;
	private Handofgod handofgod;
	private int reward;

	protected String getOperator() {
		return handofgod.getBeneficial() ? "+=" : "-=";
	}

	@Transactional
	protected void occur() {
		try {
			target = Character.findRandomByOnline(1).get(0);
			handofgod = Handofgod.findRandom();
	
			int exp = target.getExperience();
			reward = handofgod.getReward(exp);
	
			target.setExperience(target.getExperience() + reward);
	
			target.merge();
		
		} catch(NotEnoughPlayersError e) {
			LOGGER.info("Not enough players for this Random Event to occur.");
		}
	}

	public String getAnnouncement() {
		StringBuilder buf = new StringBuilder()

		.append("Hand Of God: ")

		.append(handofgod.getDescription()).append(". ")

		.append(expAnnouncement).append(".");

		return buf.toString().replace("${player}", target.getName()).replace("${operator}", getOperator())
				.replace("${reward}", String.valueOf(Math.abs(reward)));
	}
	
	@Override
	public int getTimesPerDay() {
		return Integer.parseInt(Configuration.getValue(ConfigurationKey.HANDS_OF_GOD_PER_DAY));
	}

}