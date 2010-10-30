package com.aldaviva.autorpg.game.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.AutoRPGException.LoginFailedBadPasswordError;
import com.aldaviva.autorpg.AutoRPGException.LoginFailedNoSuchPlayerError;
import com.aldaviva.autorpg.data.entities.Player;
import com.aldaviva.autorpg.display.irc.Message;
import com.aldaviva.autorpg.game.PlayerManager;

@Configurable
public class LoginAction implements PlayerAction {

	@Autowired
	private PlayerManager playerManager;

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirstArg) throws AutoRPGException {
		String playerName = argv[1];
		String password = argsExceptFirstArg;
		Player player;
		try {
			player = playerManager.login(playerName, password, userhost);
		} catch (LoginFailedBadPasswordError e){
			return "bad password";
		} catch (LoginFailedNoSuchPlayerError e){
			return Message.LOGIN_FAILED_NO_SUCH_USER_SUGGESTION.fillIn();
		}
		return "Logged in as "+player.getName()+".";
	}

	@Override
	public boolean isCheat() {
		return false;
	}

	@Override
	public String getDescription() {
		return "Players can log in so their characters accumulate experience.";
	}

}
