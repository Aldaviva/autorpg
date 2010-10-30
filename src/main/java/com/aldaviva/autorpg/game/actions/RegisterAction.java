package com.aldaviva.autorpg.game.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.irc.Message;
import com.aldaviva.autorpg.game.PlayerManager;

@Configurable
public class RegisterAction implements PlayerAction {

	@Autowired
	private PlayerManager playerManager;

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirstArg) throws AutoRPGException {
		String playerName = argv[1];
		String password = argsExceptFirstArg;
		playerManager.register(userhost, playerName, password);
		return Message.REGISTERED_SUCCESS.fillIn("playerName", playerName) + "\n"
			+ Message.CREATE_HINT.fillIn("botNickname", Configuration.getValue(ConfigurationKey.BOT_NICKNAME));
	}

	@Override
	public boolean isCheat() {
		return false;
	}

	@Override
	public String getDescription() {
		return "Makes a new Player account, representing you as a human participant outside of the game realm.\n"
		+ "If you want multiple Characters, you should only register one Player account, and CREATE multiple Characters for it.";
	}

}
