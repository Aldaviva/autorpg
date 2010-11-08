package com.aldaviva.autorpg.game;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aldaviva.autorpg.AutoRPGException.NotEnoughPlayersError;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Quest;
import com.aldaviva.autorpg.display.bulletin.Bulletin;
import com.aldaviva.autorpg.display.bulletin.BulletinManager;
import com.aldaviva.autorpg.display.bulletin.Message;

@Component
public class QuestManager implements PeriodicUpdater {

	private static final Logger LOGGER = LoggerFactory.getLogger(QuestManager.class);
	
	@Autowired
	private BulletinManager bulletinManager;
	
	@Transactional
	public Quest start(){
		try {
			Quest quest = Quest.findRandomByInactive();
		
			List<Character> characters = Character.findRandomByOnline(Quest.CHARACTERS_PER_QUEST);
			quest.setCharacters(new HashSet<Character>(characters));
			for(Character character : quest.getCharacters()){
				character.setQuest(quest);
			}
			LOGGER.debug("getCharacters: "+quest.getCharacters());
			LOGGER.debug("character.getQuest: "+characters.get(0).getQuest());
			quest.setExpRemaining(quest.getExpTotal());
			quest.setStep(1);
			stepReached(quest);
			
			bulletinManager.publish(stepReached(quest));
			
			quest.merge();
			
			return quest;
		} catch (NotEnoughPlayersError e) {
			return null;
		} catch (EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Transactional
	public void update(){
		List<Quest> quests = Quest.findByActive();
		LOGGER.debug("Updating "+quests.size()+" quests.");
		for(Quest quest : quests){
			updateQuest(quest);
			quest.merge();
		}
	}
	
	private void updateQuest(Quest quest){
		int onlineCharacters = quest.countOnlineCharacters();
		if(onlineCharacters == 0){
			remove(quest);
			bulletinManager.publish(new Bulletin("Every character in this quest is offline. Quest over."));
		} else {
			LOGGER.debug("Updating quest progress ("+onlineCharacters+" of its characters are online)");
			int experienceDelta = (onlineCharacters * onlineCharacters);
			quest.setExpRemaining(quest.getExpRemaining() - experienceDelta);
			
			int step = quest.calculateCurrentStep();
			if(quest.getStep() != step){
				quest.setStep(step);
				bulletinManager.publish(stepReached(quest));
			}
		}
	}
	
	private Bulletin stepReached(Quest quest){
		Bulletin result = new Bulletin();
		
		LOGGER.debug("quest reached step "+quest.getStep());
		
		switch(quest.getStep()){
			case 1: //just starting the quest
				Iterator<Character> characterIterator = quest.getCharacters().iterator();
				result.add(Message.QUEST_HAS_CHARACTERS.fillIn("characterA", characterIterator.next().getName(), "characterB", characterIterator.next().getName(), "characterC", characterIterator.next().getName(), "characterD", characterIterator.next().getName()));
				result.add(quest.getMission());
				result.add(quest.getStep1());
				break;
			case 2:
				result.add(quest.getStep1done());
				result.add(quest.getStep2());
				break;
			case 3:
				result.add(quest.getStep2done());
				result.add(quest.getStep3());
				break;
			case 4: //done
				result.add(quest.getStep3done());
				remove(quest);
				break;
		}
		
		return result;
	}
	
	private void remove(Quest quest){
		quest.getCharacters().clear();
		quest.setStep(0);
		quest.merge();
	}
	
}
