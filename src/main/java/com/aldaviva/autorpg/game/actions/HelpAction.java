package com.aldaviva.autorpg.game.actions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.display.irc.Bot;
import com.aldaviva.autorpg.display.irc.IrcMessage;

@Configurable
public class HelpAction implements PlayerAction {

	@Autowired
	private Bot bot;
	
	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirstArg) throws AutoRPGException {
		return IrcMessage.HELP.fillIn("botNickname", bot.getNick());
	}

	@Override
	public boolean isCheat() {
		return false;
	}

	@Override
	public String getDescription() {
		return "Show help for user commands.";
	}

}
