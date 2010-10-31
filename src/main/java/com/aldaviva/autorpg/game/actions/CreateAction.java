package com.aldaviva.autorpg.game.actions;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.display.bulletin.Bulletin;
import com.aldaviva.autorpg.display.bulletin.BulletinManager;
import com.aldaviva.autorpg.display.irc.Message;
import com.aldaviva.autorpg.game.PlayerManager;

@Configurable
public class CreateAction implements PlayerAction {

	@Autowired
	private PlayerManager playerManager;
	
	@Autowired
	private BulletinManager bulletinManager;

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirstArg) throws AutoRPGException {
		String characterName = argv[1];
		String genderString = argv[2];
		boolean female = genderString.substring(0, 1).equalsIgnoreCase("f");
		String designation = StringUtils.split(argsExceptFirstArg, null, 2)[1];
		Character character = playerManager.createCharacter(userhost, characterName, designation, female);
		bulletinManager.publish(new Bulletin(Message.CREATED_AVATAR.fillIn("name", character.getName(), "designation", character.getDesignation())));
		return "Character created.";
	}

	@Override
	public boolean isCheat() {
		return false;
	}

	@Override
	public String getDescription() {
		return "Makes a new Character for your Player. This Character will gain experience and have adventures whenever you are online and logged in with the IdleMaster.";
	}

}
