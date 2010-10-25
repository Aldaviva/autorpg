package com.aldaviva.autorpg.game.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.game.RandomEventManager;

@Configurable
public class FindItemAction implements PlayerAction {

	@Autowired
	private RandomEventManager randomEventManager;

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirst) throws AutoRPGException {
		randomEventManager.force(2);
		return "Giving someone an item...";
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
