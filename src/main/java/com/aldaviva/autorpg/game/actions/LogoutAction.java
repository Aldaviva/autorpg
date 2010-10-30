package com.aldaviva.autorpg.game.actions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.Utils;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Player;
import com.aldaviva.autorpg.display.bulletin.Bulletin;
import com.aldaviva.autorpg.display.bulletin.BulletinManager;
import com.aldaviva.autorpg.game.PlayerManager;

@Configurable
public class LogoutAction implements PlayerAction {

	@Autowired
	private PlayerManager playerManager;
	
	@Autowired
	private BulletinManager bulletinManager;

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirst) throws AutoRPGException {
		if(playerManager.logout(userhost)){
			
			Player player = Player.findByUserhost(userhost);
			List<Character> characters = Character.findCharactersByPlayer(player).getResultList();
			
			bulletinManager.publish(createCharactersDepartedBulletin(characters));
			
			return "Logged out.";
		} else {
			return "You were not logged in to start with.";
		}
	}

	private Bulletin createCharactersDepartedBulletin(List<Character> characters) {
		Bulletin bulletin = new Bulletin();
		
		if(!characters.isEmpty()){
			List<String> characterNames = new ArrayList<String>();
			for (Character character : characters) {
				characterNames.add(character.getName());
			}
			
			String namesList = Utils.commaAndList(characterNames);
			
			if(characters.size() == 1){
				namesList += " has left.";
			} else {
				namesList += " have left.";
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
		return "The currently logged-in Player will be logged out. This Player's Characters will not gain experience until the Player logs in again.";
	}

}
