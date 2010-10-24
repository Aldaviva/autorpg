package com.aldaviva.autorpg.game.events;

import java.util.ArrayList;
import java.util.List;

import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;

public class GodSend extends CalamityGodsend {

	private static String prefix = "GodSend";
	private static String operator = "+=";

	protected static final List<String> descriptions = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("${player} has found something good");
			add("Something good happened to ${player}");
			add("A godsend occurred for ${player}");
		}
	};

	@Override
	protected int getRewardMultiplier() {
		return 1;
	}
	
	@Override
	public int getTimesPerDay() {
		return Integer.parseInt(Configuration.getValue(ConfigurationKey.CALAMITIES_PER_DAY));
	}

	@Override
	protected String getPrefix() {
		return prefix;
	}

	@Override
	protected String getOperator() {
		return operator;
	}

}
