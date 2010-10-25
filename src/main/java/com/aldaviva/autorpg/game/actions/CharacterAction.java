package com.aldaviva.autorpg.game.actions;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.data.entities.Character;

public class CharacterAction implements PlayerAction {

	@Override
	public String perform(String sender, String userhost, String[] argv, String argsExceptFirst) throws AutoRPGException {
		String characterName = argv[1];
		Character character = Character.findCharacter(characterName);
		if(character != null){
			return character.toString();
		} else {
			throw new AutoRPGException.NoSuchCharacterError();
		}
	}

	@Override
	public boolean isCheat() {
		return false;
	}

	@Override
	public String getDescription() {
		return "Display the given character's attributes, such as level, experience, and items.";
	}

}
