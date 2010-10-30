package com.aldaviva.autorpg.display.irc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.Bulletin;
import com.aldaviva.autorpg.display.BulletinHandler;
import com.aldaviva.autorpg.game.PlayerManager;

@Component
@DependsOn("gameState")
public class Bot extends PircBot implements BulletinHandler {

	private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(Bot.class);

	@Autowired
	private PlayerManager playerManager;
	
	private boolean enforcedOnlineState = false;
	private List<String> userhosts = new ArrayList<String>();
	
	/* Public methods */

	public void init() {
		LOGGER.info("Initializing Bot.");
		setVerbose(true);
		setMessageDelay(250);
		setAutoNickChange(true);
		start();
	}

	public void start() {
		connectAndJoin();
	}

	@PreDestroy
	public void stop() {
		quitServer("Shutdown requested.");
		dispose();
	}

	/* Event listeners */

	@Override
	@Transactional
	protected void onPrivateMessage(String sender, String login, String hostname, String message) {
		String[] argv = StringUtils.split(message);
		//String argsExceptCommand = "";
		String argsExceptFirstArg = "";
		/*if (argv.length > 1) {
			argsExceptCommand = StringUtils.split(message, null, 2)[1];
		}*/
		if(argv.length > 2){
			argsExceptFirstArg = StringUtils.split(message, null, 3)[2];
		}
		
		
		String userhost = sender + '@' + hostname;
		IrcPlayerAction action = IrcPlayerAction.getByName(argv[0]);

		try {
			if (action == null) {
				throw new AutoRPGException.UnknownActionError();
			} else if (argv.length - 1 < action.getNumberOfRequiredArguments()) {
				throw new AutoRPGException.TooFewArgumentsError(action);
			} else {
				sendMessagesSplitByNewline(sender, action.perform(sender, userhost, argv, argsExceptFirstArg));
			}
			
		} catch (AutoRPGException e) {
			sendMessagesSplitByNewline(sender, e.getMessage());
		} catch (Exception e){
			e.printStackTrace(System.err);
		}
	}

	@Override
	protected void onConnect() {
		LOGGER.info("Connected to IRC server.");
	}
	
	@Override
	protected void onJoin(String channel, String sender, String login, String hostname) {
		if (sender.equals(Configuration.getValue(ConfigurationKey.BOT_NICKNAME))) {
			sendMessage(channel, "IdleMaster online.");
		} else {
			sendNotice(sender, "Welcome to AutoRPG.");
		}
		
		/* This will ask for all users and their userhosts in this channel.
		 * Responses will be assmebled by onServerResponse() and sent to PlayerManager.enforceOnlineUsers
		 */
		if(!enforcedOnlineState){
			sendRawLineViaQueue("who "+channel);
			enforcedOnlineState = true;
		}
		
		LOGGER.info("Joined channel.");
	}
	
	/* Triggered by onJoin */
	@Override
	protected void onServerResponse(int code, String response) {
		switch(code){
		case RPL_WHOREPLY:
			LOGGER.debug("who reply: "+response);
			String[] splitResponse = StringUtils.split(response);
			String host = splitResponse[3];
			String nick = splitResponse[5];
			userhosts.add(nick + '@' + host);
			break;
		case RPL_ENDOFWHO:
			LOGGER.debug("end of /who");
			playerManager.enforcePlayersOnlineState(userhosts);
			userhosts.clear();
			break;
		}
	}
	
	/* Behaviors */

	private void connectAndJoin() {
		String serverUrl = Configuration.getValue(ConfigurationKey.SERVER_URL);
		Integer port = Integer.valueOf(Configuration.getValue(ConfigurationKey.PORT));
		String channel = Configuration.getValue(ConfigurationKey.CHANNEL);
		String nickname = Configuration.getValue(ConfigurationKey.BOT_NICKNAME);
		LOGGER.info("Connecting to irc://"+serverUrl+":"+port+"/"+channel+" as "+nickname);
		
		try {
			setName(nickname);
			connect(serverUrl, port);
			joinChannel(channel);
		} catch (NickAlreadyInUseException e) {
			LOGGER.warn("Nickname already in use.");
		} catch (IrcException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	private void sendMessagesSplitByNewline(String target, String message) {
		String[] messages = StringUtils.split(message, '\n');
		for (String line : messages) {
			sendMessage(target, line);
		}
	}

	@Override
	public void log(String line) {
		if (line.startsWith("###")) {
			LOGGER.error(line);
		} else {
			LOGGER.debug(line);
		}
	}

	@Override
	public void handleBulletin(Bulletin bulletin) {
		sendMessagesSplitByNewline(Configuration.getValue(ConfigurationKey.CHANNEL), bulletin.toString());
	}

}
