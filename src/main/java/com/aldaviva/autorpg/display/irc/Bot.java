package com.aldaviva.autorpg.display.irc;

import java.io.IOException;

import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.Bulletin;
import com.aldaviva.autorpg.display.BulletinHandler;
import com.aldaviva.autorpg.game.PlayerManager;
import com.aldaviva.autorpg.game.RandomEventManager;

@Component
public class Bot extends PircBot implements BulletinHandler {

	@Autowired
	private PlayerManager playerManager;

	@Autowired
	private RandomEventManager randomEventManager;

	private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(Bot.class);

	/* Public methods */

	public void init() {
		LOGGER.info("Initializing Bot.");
		setVerbose(true);
		setMessageDelay(250);
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
		String[] argv = message.split("\\s");
		String argExceptFirst = "";
		if (argv.length > 1) {
			argExceptFirst = message.split("\\s", 2)[1];
		}
		String userhost = sender + '@' + hostname;
		PlayerAction action = PlayerAction.getByName(argv[0]);

		try {

			if (action == null) {

				throw new AutoRPGException.UnknownActionError();

			} else {

				String playerName, password, characterName, designation, value;
				Character character;
				ConfigurationKey configurationKey;

				if (action.getUsage() != null && argv.length - 1 != action.getUsage().getNumberOfArguments()) {
					throw new AutoRPGException.TooFewArgumentsError(action);
				}

				switch (action) {
				case LOGIN:
					playerName = argv[1];
					password = argExceptFirst;
					playerManager.login(playerName, password, userhost);
					break;

				case LOGOUT:
					playerManager.logout(userhost);
					break;

				case REGISTER:
					playerName = argv[1];
					password = argExceptFirst;
					playerManager.register(userhost, playerName, password);
					sendMessage(sender, Message.REGISTERED_SUCCESS.fillIn("playerName", playerName));
					sendMessage(sender, Message.CREATE_HINT.fillIn("botNickname", Configuration.getValue(ConfigurationKey.BOT_NICKNAME)));
					break;

				case CREATE:
					characterName = argv[1];
					designation = argExceptFirst;
					character = playerManager.createCharacter(userhost, characterName, designation);
					sendMessage(sender, Message.CREATED_AVATAR.fillIn("name", character.getName(), "class", character.getDesignation()));
					break;

				case CONFIG:
					configurationKey = ConfigurationKey.getByName(argv[1]);
					if (argv.length == 1) {
						value = Configuration.getValue(configurationKey);
						sendMessage(sender, Message.CONFIG_GET.fillIn("type", configurationKey.name(), "value", value));
					} else {
						value = argExceptFirst;
						Configuration.findConfiguration(configurationKey).setValue(value);
						sendMessage(sender, Message.CONFIG_SET.fillIn("type", configurationKey.name(), "value", value));
					}
					break;
					
				case CHARACTER:
					characterName = argv[1];
					character = Character.findCharacter(characterName);
					if(character != null){
						sendMessagesSplitByNewline(sender, character.toString());
					} else {
						throw new AutoRPGException.NoSuchCharacterError();
					}
					break;
					
				case FINDITEM:
					randomEventManager.force(2);
					break;

				default:
					sendMessage(sender, "Unimplemented.");
					break;

				}
			}
		} catch (AutoRPGException e) {
			sendMessagesSplitByNewline(sender, e.getProblem() + " " + e.getSuggestion());
		} catch (Exception e){
			e.printStackTrace(System.err);
		}
	}
	
	@Override
	protected void onJoin(String channel, String sender, String login, String hostname) {
		if (sender.equals(Configuration.getValue(ConfigurationKey.BOT_NICKNAME))) {
			sendMessage(channel, "IdleMaster online.");
		} else {
			sendNotice(sender, "Welcome to AutoRPG.");
		}
		LOGGER.info("Joined channel.");
	}
	
	@Override
	protected void onConnect() {
		LOGGER.info("Connected to IRC server.");
	}

	/* Behaviors */

	private void connectAndJoin() {
		try {
			setName(Configuration.getValue(ConfigurationKey.BOT_NICKNAME));
			connect(Configuration.getValue(ConfigurationKey.SERVER_URL), Integer.valueOf(Configuration.getValue(ConfigurationKey.PORT)));
			joinChannel(Configuration.getValue(ConfigurationKey.CHANNEL));
		} catch (NickAlreadyInUseException e) {
			LOGGER.warn("Nickname already in use.");
		} catch (IrcException e) {
			LOGGER.error(e);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	private void sendMessagesSplitByNewline(String target, String message) {
		String[] messages = message.split("\\n");
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
	public void handle(Bulletin bulletin) {
		sendMessagesSplitByNewline(Configuration.getValue(ConfigurationKey.CHANNEL), bulletin.toString());
	}

}
