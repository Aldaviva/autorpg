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
	QUESTS_PER_DAY("1"),
	LOOT_PER_DAY("32"),
	BATTLES_PER_DAY("128"),
	TEAM_BATTLES_PER_DAY("16"),
	BOT_NICKNAME("IdleMaster"),
	SERVER_URL("irc.aldaviva.com"),
	PORT("6667"),
	CHANNEL("#idlerpg"),
	SUPPORT_CONTACT("ben@aldaviva.com");

	
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
