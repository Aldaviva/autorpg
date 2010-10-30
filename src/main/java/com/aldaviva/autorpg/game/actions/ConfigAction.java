package com.aldaviva.autorpg.game.actions;

import org.apache.commons.lang.StringUtils;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.entities.Player;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.irc.Message;

public class ConfigAction implements PlayerAction {

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirst) throws AutoRPGException {
		Player player = Player.findByOnlineAndUserhost(userhost);
		if(player != null && player.getSuperuser()){
		
			if(argv.length == 1){
				StringBuilder str = new StringBuilder("Configuration values:\n");
				for (ConfigurationKey configurationKey : ConfigurationKey.values()) {
					str.append(getSingleValue(configurationKey)+"\n");
				}
				return str.toString();
			}
			
			ConfigurationKey configurationKey = ConfigurationKey.getByName(argv[1]);
			
			if (argv.length == 2) {
				return getSingleValue(configurationKey);
				
			} else {
				String value = StringUtils.split(argsExceptFirst, null, 2)[1];
				Configuration.findConfiguration(configurationKey).setValue(value);
				return Message.CONFIG_SET.fillIn("type", configurationKey.name(), "value", value);
			}
		}
		
		return "Requires superuser privileges.";
	}
	
	private String getSingleValue(ConfigurationKey configurationKey){
		String value = Configuration.getValue(configurationKey);
		return Message.CONFIG_GET.fillIn("type", configurationKey.name(), "value", value);
	}

	@Override
	public boolean isCheat() {
		return true;
	}

	@Override
	public String getDescription() {
		return "Change the game's parameters.";
	}

}
