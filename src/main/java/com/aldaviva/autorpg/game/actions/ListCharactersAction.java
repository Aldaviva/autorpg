package com.aldaviva.autorpg.game.actions;

import com.aldaviva.autorpg.AutoRPGException;

public class ListCharactersAction implements PlayerAction {

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirstArg) throws AutoRPGException {
		return "Not implemented.";
	}

	@Override
	public boolean isCheat() {
		return false;
	}

	@Override
	public String getDescription() {
		return "List all characters from all players.";
	}

}
