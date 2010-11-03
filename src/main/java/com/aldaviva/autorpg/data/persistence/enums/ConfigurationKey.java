package com.aldaviva.autorpg.data.persistence.enums;

import java.util.HashMap;
import java.util.Map;

public enum ConfigurationKey {

	MAP_WIDTH("250"),
	MAP_HEIGHT("250"),
	SECONDS_TO_LEVEL_CAP("31556926"),
	LEVEL_CAP("99"),
	LEVEL_CURVE("0.4"),
	GODSENDS_PER_DAY("8"),
	CALAMITIES_PER_DAY("8"),
	HANDS_OF_GOD_PER_DAY("2"),
	HAND_OF_GOD_EXP_MULTIPLIER_MIN("0.2"),
	HAND_OF_GOD_EXP_MULTIPLIER_MAX("0.5"),
	QUESTS_PER_DAY("1"),
	ITEMS_PER_CHARACTER_PER_DAY("3"),
	NORMAL_ITEMS_PER_RARE("20"),
	BATTLES_PER_DAY("128"),
	TEAM_BATTLES_PER_DAY("16"),
	BOT_NICKNAME("AutoRPG-IdleMaster"),
	NICKSERV_PASSWORD("!autorpg!"),
	SERVER_URL("irc.aldaviva.com"),
	PORT("6667"),
	CHANNEL("#autorpg"),
	SUPPORT_CONTACT("ben@aldaviva.com"),
	TWITTER_ENABLED("false"),
	TWITTER_OAUTH_TOKEN(""),
	TWITTER_OAUTH_TOKEN_SECRET(""),
	TWITTER_CONSUMER_KEY(""),
	TWITTER_CONSUMER_SECRET("");

	
	private static final Map<String, ConfigurationKey> stringMap = new HashMap<String, ConfigurationKey>();
	
	private final String defaultValue;

	static {
		for (ConfigurationKey param: values()) {
			stringMap.put(param.getDatabaseName(), param);
		}
	}
	
	private ConfigurationKey(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String getDatabaseName(){
		return name().toLowerCase(); 
	}
	
	public static ConfigurationKey getByName(String string){
		return stringMap.get(string);
	}
	
	
	public String getDefaultValue() {
		return defaultValue;
	}
}
