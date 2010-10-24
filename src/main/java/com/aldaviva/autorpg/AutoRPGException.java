package com.aldaviva.autorpg;

import java.util.List;

import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.irc.Message;
import com.aldaviva.autorpg.display.irc.PlayerAction;

public abstract class AutoRPGException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final String problem;
	private final String suggestion;
	
	protected AutoRPGException(String problem, String suggestion){
		this.problem = problem;
		this.suggestion = suggestion;
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
			super("Could not find the requested player.", Message.LOGIN_FAILED_NO_SUCH_USER_SUGGESTION.fillIn());
		}
	}
	
	public static class NoSuchCharacterError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public NoSuchCharacterError() {
			super("Could not find the requested character.", Message.LOGIN_FAILED_NO_SUCH_USER_SUGGESTION.fillIn());
		}
	}
	
	
	public static class LoginFailedBadPasswordError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public LoginFailedBadPasswordError() {
			super("Incorrect password.", Message.WRONG_PASSWORD_SUGGESTION.fillIn("adminEmail", Configuration.getValue(ConfigurationKey.SUPPORT_CONTACT)));
		}
	}
	
	public static class DuplicatePlayerError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public DuplicatePlayerError() {
			super("There is already a player with your nickname and IP address.", Message.MULTIPLE_CHARACTERS_SUGGESTION.fillIn());
		}
	}
	
	public static class MustRegisterToCreateAvatarError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public MustRegisterToCreateAvatarError() {
			super("You are not logged in.", Message.LOGIN_REQUIRED.fillIn());
		}
	}
	
	public static class TooFewArgumentsError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		private PlayerAction action;
		public TooFewArgumentsError(PlayerAction action){
			super("Usage:", null);
			this.action = action;
		}
		@Override
		public String getSuggestion() {
			return action.getUsage().render();
		}
	}
	
	public static class UnknownActionError extends StupidUserError {
		private static final long serialVersionUID = 1L;
		public UnknownActionError(){
			super("Unknown action.", null);
		}
		@Override
		public String getSuggestion() {
			StringBuilder str = new StringBuilder("Available action are ");
			
			List<PlayerAction> actions = PlayerAction.valuesWithoutCheats();
			
			int length = actions.size();
			
			for (int i = 0; i < length; i++) {
				PlayerAction action = actions.get(i);
				if(i>0){
					str.append(", ");
				}
				if(i == length - 1){
					str.append("and ");
				}
				str.append(action.toString());
				if(i == length - 1){
					str.append(".");
				}
			}
			
			return str.toString();
		}
	}
	
	public static class NotEnoughPlayersError extends AutoRPGException {
		private static final long serialVersionUID = 1L;
		public NotEnoughPlayersError() {
			super("Not enough players.", "Don't run this event.");
		}
	}
}
