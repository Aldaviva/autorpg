package com.aldaviva.autorpg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.irc.IrcPlayerAction;
import com.aldaviva.autorpg.display.irc.IrcMessage;

public abstract class AutoRPGException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final String problem;
	private final String suggestion;
	
	protected AutoRPGException(String problem, String suggestion){
		this.problem = problem;
		this.suggestion = suggestion;
	}
	
	@Override
	public String getMessage() {
		return getProblem()+" "+getSuggestion();
	}

	public String getProblem() {
		return problem;
	}
	public String getSuggestion() {
		return suggestion;
	}
	
	
	public static class StupidUserError extends AutoRPGException {
		private static final long serialVersionUID = 1L;
		public StupidUserError(String problem, String suggestion){
			super(problem, suggestion);
		}
	}
	
	public static class LoginFailedNoSuchPlayerError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public LoginFailedNoSuchPlayerError() {
			super("Could not find the requested player.", IrcMessage.LOGIN_FAILED_NO_SUCH_USER_SUGGESTION.fillIn());
		}
	}
	
	public static class NoSuchCharacterError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public NoSuchCharacterError() {
			super("Could not find the requested character.", IrcMessage.LOGIN_FAILED_NO_SUCH_USER_SUGGESTION.fillIn());
		}
	}
	
	
	public static class LoginFailedBadPasswordError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public LoginFailedBadPasswordError() {
			super("Incorrect password.", IrcMessage.WRONG_PASSWORD_SUGGESTION.fillIn("adminEmail", Configuration.getValue(ConfigurationKey.SUPPORT_CONTACT)));
		}
	}
	
	public static class DuplicatePlayerError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public DuplicatePlayerError() {
			super("There is already a player with your nickname and IP address.", IrcMessage.MULTIPLE_CHARACTERS_SUGGESTION.fillIn());
		}
	}
	
	public static class MustRegisterToCreateAvatarError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public MustRegisterToCreateAvatarError() {
			super("You are not logged in.", IrcMessage.LOGIN_REQUIRED.fillIn());
		}
	}
	
	public static class TooFewArgumentsError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		private IrcPlayerAction action;
		public TooFewArgumentsError(IrcPlayerAction action){
			super("Usage:", null);
			this.action = action;
		}
		@Override
		public String getSuggestion() {
			return action.usage();
		}
	}
	
	public static class UnknownActionError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public UnknownActionError(){
			super("Unknown action.", null);
		}
		@Override
		public String getSuggestion() {
			List<String> actionNamesNoCheats = new ArrayList<String>();
			
			for (IrcPlayerAction action : IrcPlayerAction.values()) {
				if(!action.isCheat()){
					actionNamesNoCheats.add(action.getCommand());
				}
			}
			
			return "Available actions: "+StringUtils.join(actionNamesNoCheats, ", ");
		}
	}
	
	public static class NotEnoughPlayersError extends AutoRPGException {
		private static final long serialVersionUID = 1L;
		public NotEnoughPlayersError() {
			super("Not enough players.", "Don't run this event.");
		}
	}
	
	public static class ConfigurationIncompleteError extends AutoRPGException {
		private static final long serialVersionUID = 1L;
		public ConfigurationIncompleteError(ConfigurationKey key) {
			super("Configuration for "+key.name()+" is incomplete.", IrcMessage.CONFIGURATION_INCOMPLETE_HINT.fillIn("configurationKey", key.name()));
		}
	}
}
