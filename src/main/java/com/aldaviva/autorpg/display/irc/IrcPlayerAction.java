package com.aldaviva.autorpg.display.irc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jibble.pircbot.Colors;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.data.enums.ConfigurationKey;
import com.aldaviva.autorpg.game.actions.CharacterAction;
import com.aldaviva.autorpg.game.actions.CheatAction;
import com.aldaviva.autorpg.game.actions.ConfigAction;
import com.aldaviva.autorpg.game.actions.CreateAction;
import com.aldaviva.autorpg.game.actions.HelpAction;
import com.aldaviva.autorpg.game.actions.LoginAction;
import com.aldaviva.autorpg.game.actions.LogoutAction;
import com.aldaviva.autorpg.game.actions.PlayerAction;
import com.aldaviva.autorpg.game.actions.RegisterAction;

public abstract class IrcPlayerAction implements PlayerAction {

	private Class<? extends PlayerAction> playerActionClass;
	private List<String> argNames;
	private List<String> argDescriptions;
	private List<String> argExamples;
	
	private static PlayerAction INSTANCE;
	
	private IrcPlayerAction(Class<? extends PlayerAction> playerActionClass, List<String> argNames, List<String> argDescriptions, List<String> argExamples) {
		this.playerActionClass = playerActionClass;
		
		if(argNames.size() != argDescriptions.size() || argNames.size() != argExamples.size()){
			throw new IllegalArgumentException("The names, descriptions, and examples of IrcPlayerAction arguments must all have the same number of items.");
		}
		
		this.argNames = argNames;
		this.argDescriptions = argDescriptions;
		this.argExamples = argExamples;
	}
	
	private IrcPlayerAction(Class<? extends PlayerAction> playerActionClass){
		this(playerActionClass, Collections.<String>emptyList(), Collections.<String>emptyList(), Collections.<String>emptyList());
	}
	
	public String getName(){
		return getClass().getSimpleName().toUpperCase();
	}

	public Class<? extends PlayerAction> getPlayerActionClass() {
		return playerActionClass;
	}

	public List<String> getArgNames() {
		return argNames;
	}

	public List<String> getArgDescriptions() {
		return argDescriptions;
	}

	public List<String> getArgExamples() {
		return argExamples;
	}
	
	protected int getNumberOfRequiredArguments(){
		return getArgNames().size();
	}
	
	private PlayerAction getInstance(){
		if(INSTANCE == null){
			try {
				INSTANCE = getPlayerActionClass().newInstance();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return INSTANCE;
	}
	
	public String getUsage(){
		StringBuilder str = new StringBuilder(getSyntax());
		str.append('\n'+Colors.NORMAL+getInstance().getDescription()+'\n');
		//for (Entry<String, String> argumentPair : getArguments().entrySet()) {
		for(int i=0; i<getArgNames().size(); i++){
			str.append(Colors.BOLD+Colors.UNDERLINE+getArgNames().get(i));
			str.append(Colors.NORMAL+" - "+getArgDescriptions().get(i)+'\n');
		}
		str.append("Example: "+Colors.RED + getName() + " " + StringUtils.join(getArgExamples(), " ") + Colors.NORMAL);
		return str.toString();
	}
	
	public String getSyntax(){
		StringBuilder str = new StringBuilder(Colors.NORMAL + Colors.BOLD + getName() + Colors.NORMAL);
		for (String argName : getArgNames()) {
			str.append(' '+Colors.BOLD+Colors.UNDERLINE+argName+Colors.NORMAL);
		}
		return str.toString();
	}
	
	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirstArg) throws AutoRPGException {
		return getInstance().perform(sender, userhost, argv, argsExceptFirstArg);
	}

	@Override
	public boolean isCheat() {
		return getInstance().isCheat();
	}

	@Override
	public String getDescription() {
		return getInstance().getDescription();
	}
	
	public static List<IrcPlayerAction> getAllActions(){
		Class<?>[] classes = IrcPlayerAction.class.getClasses();
		List<IrcPlayerAction> result = new ArrayList<IrcPlayerAction>();
		for (Class<?> clazz : classes) {
			try {
				result.add((IrcPlayerAction) clazz.newInstance());
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return result;
	}

	public static class Register extends IrcPlayerAction {
		public Register(){
			super(RegisterAction.class,
				Arrays.<String>asList("player name", "password"),
				Arrays.<String>asList("Uniquely identifies you, the human player. Will not appear in-game.", "Used to LOGIN with the IdleMaster. Stored with SHA256 hashing and salt."),
				Arrays.<String>asList("Robert", "secretpassword"));
		}
	}
	
	public static class Create extends IrcPlayerAction {
		public Create(){
			super(CreateAction.class,
				Arrays.<String>asList("character name", "gender", "designation"),
				Arrays.<String>asList("Your character's name as it will appear in-game.", "Female or Male", "The designation or class name of your choosing."),
				Arrays.<String>asList("Daemar", "Female", "Magical Girl"));
		}
	}
	
	public static class Character extends IrcPlayerAction {
		public Character(){
			super(CharacterAction.class,
				Arrays.<String>asList("character name"),
				Arrays.<String>asList("The name of the Character you wish to see information about."),
				Arrays.<String>asList("Daemar"));
		}
	}
	
	public static class Config extends IrcPlayerAction {
		public Config(){
			super(ConfigAction.class,
				Arrays.<String>asList("[parameter]", "[value]"),
				Arrays.<String>asList("Leave blank to see the values of all parameters", "New value for the given parameter. Leave blank to see the value of the given parameter."),
				Arrays.<String>asList(ConfigurationKey.values()[0].name(), "250"));
		}

		@Override
		protected int getNumberOfRequiredArguments() {
			return 0;
		}
	}
	
	public static class Login extends IrcPlayerAction {
		public Login(){
			super(LoginAction.class,
				Arrays.<String>asList("player name", "password"),
				Arrays.<String>asList("The player name you used with the REGISTER command.", "The password you used with the REGISTER command."),
				Arrays.<String>asList("Robert", "secretpassword"));
		}
	}
	
	public static class Logout extends IrcPlayerAction {
		public Logout(){
			super(LogoutAction.class);
		}
	}
	
	public static class Help extends IrcPlayerAction {
		public Help(){
			super(HelpAction.class);
		}
	}
	
	public static class Cheat extends IrcPlayerAction {
		public Cheat(){
			super(CheatAction.class,
				Arrays.<String>asList("code"),
				Arrays.<String>asList("Which cheat code to use."),
				Arrays.<String>asList("finditem"));
		}
	}
	
}
