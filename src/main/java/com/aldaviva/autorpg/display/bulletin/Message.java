package com.aldaviva.autorpg.display.bulletin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.aldaviva.autorpg.Utils;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Item;
import com.aldaviva.autorpg.data.entities.Quest;

public abstract class Message {

	String message;
	
	protected Message(String message){
		this.message = message;
	}
	
	public String toString(){
		return message;
	}
	
	private static String getCommaSeparatedCharacterNames(Iterable<Character> characters){
		List<String> characterNames = new ArrayList<String>();
		for(Character character : characters){
			characterNames.add(Style.CHARACTER_NAME+character.getName()+Style.NORMAL);
		}
		return Utils.commaAndList(characterNames);
	}
	
	public static class CharacterFoundItem extends Message {
		public CharacterFoundItem(Character character, Item item){
			super(Style.CHARACTER_NAME + character.getName() + Style.NORMAL + " has found " + item.getArticle() + (item.getRare() ? Style.ITEM_RARE_NAME : Style.ITEM_NAME) + item.getName() + Style.NORMAL + " (level "+item.getLevel()+").");
		}
	}
	
	public static class HandOfGodReward extends Message {
		public HandOfGodReward(Character character, String operator, int reward){
			super(StringUtils.capitalize(character.getPossessivePronoun()) + " experience " + operator + " " +reward);
		}
	}
	
	public static class CharacterLevelsUp extends Message {
		public CharacterLevelsUp(Character character){
			super(Style.CHARACTER_NAME + character.getName() + Style.NORMAL +" has reached Level " + character.getLevel() + ".");
		}
	}
	
	public static class QuestHasCharacters extends Message {
		public QuestHasCharacters(Quest quest){
			super(getCommaSeparatedCharacterNames(quest.getCharacters()) + " are on a quest.");
		}
	}
	
	public static class CharactersFoundItems extends Message {
		public CharactersFoundItems(Iterable<Character> characters){
			super(getCommaSeparatedCharacterNames(characters) + " have found a treasure chest.");
		}
	}
	
	public static class CharactersAllGainExperience extends Message {
		public CharactersAllGainExperience(Iterable<Character> characters, int experienceGain){
			super(getCommaSeparatedCharacterNames(characters) + " each gain "+experienceGain + " experience.");
		}
	}
	
}
