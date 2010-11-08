package com.aldaviva.autorpg.game.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.data.entities.Player;
import com.aldaviva.autorpg.game.RandomEventManager;

@Configurable
public class CheatAction implements PlayerAction {

	@Autowired
	private RandomEventManager randomEventManager;

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirst) throws AutoRPGException {
		
		Player player = Player.findByOnlineAndUserhost(userhost);
		if(player == null || !player.getSuperuser()){
			return "Cheats require superuser privileges.";
		}
		
		String cheatCommand = argv[1];
		
		if(cheatCommand.equalsIgnoreCase("finditem")){
			randomEventManager.force(1);
			return "Giving someone an item...";
		} else if(cheatCommand.equalsIgnoreCase("handofgod")){
			randomEventManager.force(0);
			return "Triggering Hand of God...";
		} else if(cheatCommand.equalsIgnoreCase("startquest")){
			randomEventManager.force(2);
			return "Starting a quest...";
		} else {
			return "Cheat "+cheatCommand+" unrecognized.";
		}
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
