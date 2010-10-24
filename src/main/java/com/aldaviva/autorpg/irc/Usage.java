package com.aldaviva.autorpg.irc;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.jibble.pircbot.Colors;

public enum Usage {

	REGISTER(
		"Makes a new Player account, representing you as a human participant outside of the game realm.\n"
			+ "If you want multiple Characters, you should only register one Player account, and CREATE multiple Characters for it.",
		new LinkedHashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				put("username", "Uniquely identifies you, the human player. Will not appear in-game.");
				put("password", "Used to LOGIN with the IdleMaster. Stored with SHA256 hashing and salt.");
			}},
		"REGISTER Robert secretpassword"),
	CREATE(
		"Makes a new Character for your Player. This Character will gain experience and have adventures whenever you are online and logged in with the IdleMaster.",
		new LinkedHashMap<String, String>(){
			private static final long serialVersionUID = 1L;
			{
				put("character name", "Your character's name as it will appear in-game.");
				put("class", "The class name of your choosing.");
			}},
		"CREATE Daemar Magical Girl"),
	CHARACTER(
		"Display the given character's attributes, such as level, experience, and items.",
		new LinkedHashMap<String, String>(){
			private static final long serialVersionUID = 1L;
			{
				put("character name", "The name of the Character you wish to see information about.");
			}},
		"CHARACTER Daemar"
		);

	private String description;
	private String example;
	

	private LinkedHashMap<String, String> arguments;

	private Usage(String description, LinkedHashMap<String, String> arguments, String example) {
		this.description = description;
		this.example = example;
		this.arguments = arguments;
	}

	public String getDescription() {
		return description;
	}

	public LinkedHashMap<String, String> getArguments() {
		return arguments;
	}

	public String getCommand() {
		return name();
	}
	
	public String getExample() {
		return example;
	}
	
	public int getNumberOfArguments(){
		return arguments.size();
	}
	
	public String render(){
		StringBuilder buf = new StringBuilder();
		buf.append(Colors.BOLD).append(getCommand().toUpperCase()).append(Colors.NORMAL);
		for (String argument : getArguments().keySet()) {
			buf.append(' ').append(Colors.BOLD).append(Colors.UNDERLINE).append(argument).append(Colors.NORMAL);
		}
		buf.append("\n").append(Colors.NORMAL).append(getDescription()).append("\n");
		for (Entry<String, String> argumentPair : getArguments().entrySet()) {
			buf.append(Colors.BOLD).append(Colors.UNDERLINE).append(argumentPair.getKey());
			buf.append(Colors.NORMAL).append(" - ").append(argumentPair.getValue()).append("\n");
		}
		buf.append("Example: ").append(getExample());
		return buf.toString();
	}
}
