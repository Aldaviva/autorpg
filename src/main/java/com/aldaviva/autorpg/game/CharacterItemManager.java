package com.aldaviva.autorpg.game;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Item;

@Component
public class CharacterItemManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterItemManager.class);
	
	public void init(){
		LOGGER.info("Initializing Character Item Manager.");
	}
	
	public void offerCharacterAnItem(Item newItem, Character character){
		Item toDiscard = itemToDiscard(character, newItem);
		if(toDiscard == null){
			addItemToCharacter(character, newItem);
		} else if(toDiscard.equals(newItem)){
			discardNewItem(character, toDiscard);
		} else {
			discardExistingItem(character, toDiscard);
			addItemToCharacter(character, newItem);
		}
	}
	
	private Item itemToDiscard(Character character, Item newItem) {
		List<Item> existingItems = Item.findItemsByCharacterAndSlot(character, newItem.getSlot());
		if (existingItems.size() < newItem.getSlot().getSlotsPerCharacter()) {
			return null;
		}

		Item shortestNameItem = newItem;

		for (int i = 0; i < existingItems.size(); i++) {
			Item existingItem = existingItems.get(i);
			if (existingItem.getName().length() < shortestNameItem.getName().length()) {
				shortestNameItem = existingItem;
			}
		}

		return shortestNameItem;
	}

	private void addItemToCharacter(Character character, Item item) {
		character.getItems().add(item);
		character.merge();
		LOGGER.info(character.getName() + " equips this " + item.getSlot().getPreposition() + " " + character.getPossessivePronoun() + " " + item.getSlot());
	}

	private void discardNewItem(Character character, Item item) {
		LOGGER.info("The new item has a shorter name than " + character.getName() + "'s similar items, so it is discarded.");
	}

	private void discardExistingItem(Character character, Item item) {
		LOGGER.info("The new item has a longer name than " + item.getArticle() + item.getName() + ", so it is replaced in " + character.getName()
				+ "'s inventory.");
		character.getItems().remove(item);
	}
}
