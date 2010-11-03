package com.aldaviva.autorpg.game.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.aldaviva.autorpg.AutoRPGException.NotEnoughPlayersError;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.entities.Handofgod;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.bulletin.Message;

public class HandofGodEvent extends RandomEvent {

	private static final Logger LOGGER = LoggerFactory.getLogger(HandofGodEvent.class);
	
	private Character target;
	private Handofgod handofgod;
	private int reward;

	protected String getOperator() {
		return handofgod.getBeneficial() ? "gains" : "loses";
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

//		.append("Hand Of God: ")

		.append(handofgod.getDescription().replace("${player}", target.getName())).append(" ")

		.append(Message.HANDOFGOD_REWARD.fillIn("player", target.getName(), "operator", getOperator(), "reward", String.valueOf(Math.abs(reward))));

		return buf.toString();
	}
	
	@Override
	public int getTimesPerDay() {
		return Integer.parseInt(Configuration.getValue(ConfigurationKey.HANDS_OF_GOD_PER_DAY));
	}

}