package com.aldaviva.autorpg.game.actions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.AutoRPGException.LoginFailedBadPasswordError;
import com.aldaviva.autorpg.AutoRPGException.LoginFailedNoSuchPlayerError;
import com.aldaviva.autorpg.Utils;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Player;
import com.aldaviva.autorpg.display.bulletin.Bulletin;
import com.aldaviva.autorpg.display.bulletin.BulletinManager;
import com.aldaviva.autorpg.display.irc.Message;
import com.aldaviva.autorpg.game.PlayerManager;

@Configurable
public class LoginAction implements PlayerAction {

	@Autowired
	private PlayerManager playerManager;
	
	@Autowired
	private BulletinManager bulletinManager;

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirstArg) throws AutoRPGException {
		String playerName = argv[1];
		String password = argsExceptFirstArg;
		Player player;
		try {
			player = playerManager.login(playerName, password, userhost);
			
			List<Character> characters = Character.findCharactersByPlayer(player).getResultList();
			
			bulletinManager.publish(createCharactersRejoinedBulletin(characters));
			
			return "Logged in as "+player.getName()+".";
		} catch (LoginFailedBadPasswordError e){
			return Message.WRONG_PASSWORD.fillIn();
		} catch (LoginFailedNoSuchPlayerError e){
			return Message.LOGIN_FAILED_NO_SUCH_USER_SUGGESTION.fillIn();
		}
	}
	
	public static Bulletin createCharactersRejoinedBulletin(List<Character> characters){
		Bulletin bulletin = new Bulletin();
		
		if(!characters.isEmpty()){
			List<String> characterNames = new ArrayList<String>();
			for (Character character : characters) {
				characterNames.add(character.getName());
			}
			
			String namesList = Utils.commaAndList(characterNames);
			
			if(characters.size() == 1){
				namesList += " has joined.";
			} else {
				namesList += " have joined.";
			}
			
			bulletin.add(namesList);
		}

		return bulletin;
	}

	@Override
	public boolean isCheat() {
		return false;
	}

	@Override
	public String getDescription() {
		return "Players can log in so their characters accumulate experience.";
	}

}
