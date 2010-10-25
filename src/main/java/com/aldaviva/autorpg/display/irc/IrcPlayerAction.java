package com.aldaviva.autorpg.display.irc;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.jibble.pircbot.Colors;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.game.actions.*;

public enum IrcPlayerAction implements PlayerAction {

	REGISTER(
		new RegisterAction(),
		new LinkedHashMap<String, String>() {
			private static final long serialVersionUID = 1L;
			{
				put("player name", "Uniquely identifies you, the human player. Will not appear in-game.");
				put("password", "Used to LOGIN with the IdleMaster. Stored with SHA256 hashing and salt.");
			}},
		"REGISTER Robert secretpassword"),
	
	CREATE(
		new CreateAction(),
		new LinkedHashMap<String, String>(){
			private static final long serialVersionUID = 1L;
			{
				put("character name", "Your character's name as it will appear in-game.");
				put("class", "The class name of your choosing.");
			}},
		"CREATE Daemar Magical Girl"),
		
	CHARACTER(
		new CharacterAction(),
		new LinkedHashMap<String, String>(){
			private static final long serialVersionUID = 1L;
			{
				put("character name", "The name of the Character you wish to see information about.");
			}},
		"CHARACTER Daemar"
		),
		
	CONFIG(
		new ConfigAction(),
		new LinkedHashMap<String, String>(){
			private static final long serialVersionUID = 1L;
			{
				put("parameter", "Game parameter.");
				put("[value]", "Optional. If omitted, displays the current value. If specified, sets the parameter to the specified value.");
			}},
		0,
		new String[]{"CONFIG map_width", "CONFIG map_width 250"}
		),
		
	LOGIN(
		new LoginAction(),
		new LinkedHashMap<String, String>(){
			private static final long serialVersionUID = 1L;
			{
				put("player name", "The player name you used with the REGISTER command.");
				put("password", "The password you used with the REGISTER command.");
			}},
		"LOGIN Robert secretpassword"
		),
		
	LOGOUT(
		new LogoutAction(),
		new LinkedHashMap<String, String>(){
			private static final long serialVersionUID = 1L;
			{
				put("player name", "The player name you used with the REGISTER command.");
			}},
		"LOGOUT Robert"
		),
		
	/* Cheats */
	FINDITEM(
		new FindItemAction(),
		new LinkedHashMap<String, String>(),
		"FINDITEM"
		);

	private PlayerAction action;
	private LinkedHashMap<String, String> arguments;
	private int numberOfRequiredArguments;
	private String[] examples;

	private IrcPlayerAction(PlayerAction action, LinkedHashMap<String, String> arguments, int numberOfRequiredArguments, String[] examples) {
		this.action = action;
		this.arguments = arguments;
		this.numberOfRequiredArguments = numberOfRequiredArguments;
		this.examples = examples;
	}

	private IrcPlayerAction(PlayerAction action, LinkedHashMap<String, String> arguments, int numberOfRequiredArguments, String example) {
		this(action, arguments, numberOfRequiredArguments, new String[]{example});
	}
	
	private IrcPlayerAction(PlayerAction action, LinkedHashMap<String, String> arguments, String example) {
		this(action, arguments, arguments.size(), new String[]{example});
	}
	
	private IrcPlayerAction(PlayerAction action, LinkedHashMap<String, String> arguments, String[] examples) {
		this(action, arguments, arguments.size(), examples);
	}

	public LinkedHashMap<String, String> getArguments() {
		return arguments;
	}

	public String getCommand() {
		return name();
	}
	
	public String[] getExamples() {
		return examples;
	}
	
	public PlayerAction getAction() {
		return action;
	}
	
	public int getNumberOfArguments(){
		return arguments.size();
	}
	
	public int getNumberOfRequiredArguments(){
		return numberOfRequiredArguments;
	}
	
	@Override
	public String getDescription() {
		return getAction().getDescription();
	}
	

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirst) throws AutoRPGException {
		return getAction().perform(sender, userhost, argv, argsExceptFirst);
	}

	@Override
	public boolean isCheat() {
		return getAction().isCheat();
	}
	
	public String usage(){
		StringBuilder str = new StringBuilder(Colors.BOLD+getCommand().toUpperCase()+Colors.NORMAL);
		for (String argument : getArguments().keySet()) {
			str.append(' '+Colors.BOLD+Colors.UNDERLINE+argument+Colors.NORMAL);
		}
		str.append('\n'+Colors.NORMAL+getDescription()+'\n');
		for (Entry<String, String> argumentPair : getArguments().entrySet()) {
			str.append(Colors.BOLD+Colors.UNDERLINE+argumentPair.getKey());
			str.append(Colors.NORMAL+" - "+argumentPair.getValue()+'\n');
		}
		str.append("Example: "+StringUtils.join(examples, "\n         "));
		return str.toString();
	}
	
	static final Map<String, IrcPlayerAction> map = new HashMap<String, IrcPlayerAction>();
	
	static {
		for(IrcPlayerAction action : values()){
			map.put(action.toString(), action);
		}
	}
	
	public static IrcPlayerAction getByName(String name){
		IrcPlayerAction result = map.get(name.toUpperCase());
		return result;
	}

}
