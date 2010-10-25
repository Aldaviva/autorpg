package com.aldaviva.autorpg.game.actions;

import com.aldaviva.autorpg.AutoRPGException;

public interface PlayerAction {
	
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirst) throws AutoRPGException;
	
	public boolean isCheat();
	
	public String getDescription();
	
}
