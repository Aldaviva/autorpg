package com.aldaviva.autorpg.game.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.aldaviva.autorpg.AutoRPGException.NotEnoughPlayersError;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Item;
import com.aldaviva.autorpg.display.BulletinManager;
import com.aldaviva.autorpg.game.CharacterItemManager;

@Configurable
public class FindItem extends RandomEvent {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CalamityGodsend.class);

	private CharacterItemManager characterItemManager;
	
	public FindItem(CharacterItemManager characterItemManager) {
		this.characterItemManager = characterItemManager;
	}

	private Character character;
	private Item newItem;
	
	@Override
	public int getTimesPerDay() {
		return 16;
	}
	
	protected Item getRandomItem(){
		return Item.findItemByRandom();
	}

	@Override
	protected void occur() {
		try {
			character = Character.findOnlinePlayersByRandom(1).get(0);
			newItem = getRandomItem();
			
			//onFindItem(character, newItem);
			
			characterItemManager.offerCharacterAnItem(newItem, character);
			
		} catch(NotEnoughPlayersError e) {
			LOGGER.info("Not enough players for this Random Event to occur.");
		}
	}

	@Override
	public String getAnnouncement() {
		String result = character.getName() + " has found " + newItem.getArticle() + newItem.getName()+".";
		if(newItem.getRare()){
			result += "\nThis is a rare item!";
		}
		return result;
	}

}