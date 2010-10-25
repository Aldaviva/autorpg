package com.aldaviva.autorpg.game.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.game.PlayerManager;

@Configurable
public class LogoutAction implements PlayerAction {

	@Autowired
	private PlayerManager playerManager;

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirst) throws AutoRPGException {
		playerManager.logout(userhost);
		return "Logged out. Your characters experience is frozen until you log in again.";
	}

	@Override
	public boolean isCheat() {
		return false;
	}

	@Override
	public String getDescription() {
		return "The currently logged-in Player will be logged out. This Player's Characters will not gain experience until the Player logs in again.";
	}

}
