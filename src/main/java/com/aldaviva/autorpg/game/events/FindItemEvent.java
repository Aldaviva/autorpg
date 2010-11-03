package com.aldaviva.autorpg.game.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import com.aldaviva.autorpg.AutoRPGException.NotEnoughPlayersError;
import com.aldaviva.autorpg.Utils;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.entities.Item;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.bulletin.Message;
import com.aldaviva.autorpg.display.bulletin.Style;
import com.aldaviva.autorpg.game.CharacterItemManager;

@Configurable
public class FindItemEvent extends RandomEvent {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HandofGodEvent.class);

	private CharacterItemManager characterItemManager;
	
	public FindItemEvent(CharacterItemManager characterItemManager) {
		this.characterItemManager = characterItemManager;
	}

	private Character character;
	private Item newItem;
	private boolean keepingNewItem;
	
	@Override
	public int getTimesPerDay() {
		return (int) (Character.countCharacters() * Integer.parseInt(Configuration.getValue(ConfigurationKey.ITEMS_PER_CHARACTER_PER_DAY)));
	}
	
	protected Item getRandomItem(){
		int normalItemsPerRare = Integer.parseInt(Configuration.getValue(ConfigurationKey.NORMAL_ITEMS_PER_RARE));
		boolean findRare = (1 == Utils.getRandomInt(1, normalItemsPerRare)); 
		return Item.findRandomByRarity(findRare);
	}

	@Transactional
	@Override
	protected void occur() {
		try {
			character = Character.findRandomByOnline(1).get(0);
			newItem = getRandomItem();
			
			keepingNewItem = characterItemManager.offerCharacterAnItem(newItem, character);
			
		} catch(NotEnoughPlayersError e) {
			LOGGER.info("Not enough players for this Random Event to occur.");
		}
	}

	@Override
	public String getAnnouncement() {
		String result = "";
		String itemStyle = newItem.getRare() ? Style.ITEM_RARE_NAME : Style.ITEM_NAME;
		if(keepingNewItem){
			result = Message.CHARACTER_FOUND_ITEM.fillIn("character.name", character.getName(), "itemStyle", itemStyle, "item.article", newItem.getArticle().toString(), "item.name", newItem.getName());
		}
		return result;
	}
}