package com.aldaviva.autorpg.display.irc;

import org.jibble.pircbot.Colors;

import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Player;
import com.aldaviva.autorpg.data.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.bulletin.Message;
import com.aldaviva.autorpg.display.bulletin.Style;

public abstract class IrcMessage extends Message {

	private IrcMessage(String message){
		super(message);
	}
	
	private static String botQuery(IrcPlayerAction action){
		return Colors.NORMAL + Colors.BOLD + action.getSyntax() + Colors.NORMAL;
	}
	
	public static class RegisteredPlayerSuccessfully extends IrcMessage {
		public RegisteredPlayerSuccessfully(Player player){
			super("Welcome to the game, " + Colors.BOLD + player.getName() + Colors.NORMAL + "! You have successfully registered.");
		}
	}
	
	public static class RegisterHint extends IrcMessage {
		public RegisterHint(){
			super("To register a new Player account, use "+botQuery(new IrcPlayerAction.Register()) + ".");
		}
	}
	
	public static class CreateCharacterHint extends IrcMessage {
		public CreateCharacterHint() {
			super("Use "+botQuery(new IrcPlayerAction.Create()) + " to create a character.");
		}
	}
	
	public static class CreatedCharacterSuccessfully extends IrcMessage {
		public CreatedCharacterSuccessfully(Character character){
			super(Style.CHARACTER_NAME + character.getName() + Style.NORMAL + ", the Level 1 "+character.getDesignation() + ", has joined the realm. Hell's minions grow stronger.");
		}
	}
	
	public static class LoginFailedNoSuchUserSuggestion extends IrcMessage {
		public LoginFailedNoSuchUserSuggestion(){
			super("Try registering the user account with " + botQuery(new IrcPlayerAction.Register()) + ".");
		}
	}
	
	public static class NoSuchCharacterSuggestion extends IrcMessage {
		public NoSuchCharacterSuggestion(){
			super("Check the list of characters on the website.");
		}
	}
	
	public static class WrongPassword extends IrcMessage {
		public WrongPassword(){
			super("YOU DIDN'T SAY THE MAGIC WORD!!!");
		}
	}
	
	public static class WrongPasswordSuggestion extends IrcMessage {
		public WrongPasswordSuggestion(String adminEmail){
			super("If you have forgotten your password, contact the administrative team at " + adminEmail+".");
		}
	}
	
	public static class MultipleCharactersSuggestion extends IrcMessage {
		public MultipleCharactersSuggestion() {
			super("To create more characters for your player, use " + botQuery(new IrcPlayerAction.Cheat()) + " multiple times.");
		}
	}
	
	public static class LoginRequired extends IrcMessage {
		public LoginRequired() {
			super("To register as a new Player, use " + botQuery(new IrcPlayerAction.Register()) + ". To log in as an existing Player, use " + botQuery(new IrcPlayerAction.Login()) + ".");
		}
	}
	
	public static class ConfigSet extends IrcMessage {
		public ConfigSet(ConfigurationKey key, String value) {
			super(Colors.BOLD + key + Colors.NORMAL + " = " + value);
		}
	}
	
	public static class ConfigGet extends IrcMessage {
		public ConfigGet(ConfigurationKey key, String value) {
			super(Colors.BOLD + key + Colors.NORMAL + " == " + value);
		}
	}
	
	public static class ConfigurationIncompleteHint extends IrcMessage {
		public ConfigurationIncompleteHint(ConfigurationKey key){
			super("Try setting this configuration using " + botQuery(new IrcPlayerAction.Config()) + ".");
		}
	}
	
	public static class Help extends IrcMessage {
		public Help(Bot bot){
			super("AutoRPG is a simple IRC-based game where players remain logged in as long as possible, and their characters will automatically go on adventures.\n" +
			"To start playing, use the REGISTER command to make a Player account for yourself:\n"+
			"  "+Colors.BOLD+"/msg " + bot.getNick() + " " + botQuery(new IrcPlayerAction.Register())+"\n"+
			"Once you have a Player account, you can CREATE Characters:\n"+
			"  "+Colors.BOLD+"/msg " + bot.getNick() + " " + botQuery(new IrcPlayerAction.Create()));
		}
	}
	
	public static class WelcomeNewPlayer extends IrcMessage {
		public WelcomeNewPlayer(Bot bot){
			super("Welcome to AutoRPG. To get started, type " + Colors.BOLD+"/msg " + bot.getNick() + " HELP"+Colors.NORMAL+"." );
		}
	}
	
	public static class WelcomeRejoined extends IrcMessage {
		public WelcomeRejoined(Player player){
			super("Automatically logged in as " + player.getName() + ".");
		}
	}
}
