package com.aldaviva.autorpg.game.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.data.entities.Player;
import com.aldaviva.autorpg.game.RandomEventManager;

@Configurable
public class HandofGodAction implements PlayerAction {

	@Autowired
	private RandomEventManager randomEventManager;

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirst) throws AutoRPGException {
		Player player = Player.findByOnlineUserhost(userhost);
		if(player != null && player.getSuperuser()){
			
			randomEventManager.force(0);
			return "Triggering Hand of God...";
		}
		return "Cheats require superuser privileges.";
	}

	@Override
	public boolean isCheat() {
		return true;
	}

	@Override
	public String getDescription() {
		return "Give a random character an item. (game cheat)";
	}

}
