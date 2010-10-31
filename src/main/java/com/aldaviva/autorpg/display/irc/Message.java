package com.aldaviva.autorpg.display.irc;

import org.jibble.pircbot.Colors;

public enum Message {
	
	REGISTERED_SUCCESS("Welcome to the game, ${playerName}! You have successfully registered."),
	REGISTER_HINT("To register a new Player account, use "+botQuery("register", "playerName", "password")+"."),
	CREATE_HINT("Use "+botQuery("create", "name", "class")+" to create a character."),
	CREATED_AVATAR(Colors.BOLD+"${name}"+Colors.NORMAL+", the Level 1 ${designation}, has joined the realm."),
	LOGIN_FAILED_NO_SUCH_USER_SUGGESTION("Try creating the user account with "+botQuery("create", "name", "password")+"."),
	NO_SUCH_CHARACTER_SUGGESTION("Check the list of characters using "+botQuery("list", "characters")+"."),
	WRONG_PASSWORD("YOU DIDN'T SAY THE MAGIC WORD!!!"),
	WRONG_PASSWORD_SUGGESTION("If you have forgotten your password, contact the administrative team at ${adminEmail}."),
	MULTIPLE_CHARACTERS_SUGGESTION("To create more characters for your player, use "+botQuery("create", "name", "class")+" multiple times."),
	LOGIN_REQUIRED("To register as a new player, use "+botQuery("register", "yourName", "password")+". To log in as an existing player, use "+botQuery("login", "user", "password")),
	CONFIG_SET(Colors.BOLD+"${type}"+Colors.NORMAL+" = ${value}"),
	CONFIG_GET(Colors.BOLD+"${type}"+Colors.NORMAL+" == ${value}"),
	CONFIGURATION_INCOMPLETE_HINT("Try setting this configuration using "+botQuery("CONFIG", "${configurationKey}", "value"));
	
	private String message;
	
	private Message(final String message){
		this.message = message;
	}
	
	public String fillIn(String... replacementPairs){
		if(replacementPairs.length % 2 != 0){
			throw new IllegalArgumentException("An even number of arguments is required.");
		}
		
		String result = message;
		for(int argNum=0; argNum < replacementPairs.length; argNum += 2){
			result = result.replace("${"+replacementPairs[argNum]+"}", replacementPairs[argNum+1]);
		}
		
		return result;
	}
	
	private static String botQuery(String command, String... arguments){
		
		StringBuilder buf = new StringBuilder();
		buf.append(Colors.BOLD).append(command.toUpperCase()).append(Colors.NORMAL);
		for (String argument : arguments) {
			buf.append(' ').append(Colors.BOLD).append(Colors.UNDERLINE).append(argument).append(Colors.NORMAL);
		}
		buf.append(Colors.NORMAL);
		return buf.toString();
	}
	
	
	
}
