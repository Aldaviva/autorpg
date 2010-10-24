package com.aldaviva.autorpg.game;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aldaviva.autorpg.AutoRPGException.DuplicatePlayerError;
import com.aldaviva.autorpg.AutoRPGException.LoginFailedNoSuchPlayerError;
import com.aldaviva.autorpg.AutoRPGException.MustRegisterToCreateAvatarError;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Player;
import com.aldaviva.autorpg.data.persistence.types.MapPoint;

@Component
public class PlayerManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerManager.class);

	public Player login(String playerName, String plainPassword, String userhost) throws LoginFailedNoSuchPlayerError{
		Player player = Player.findPlayer(playerName);
		if(player != null){
			player.setOnline(true);
			player.setUserhost(userhost);
			LOGGER.info(player.getName() + " has logged in.");
		} else {
			throw new LoginFailedNoSuchPlayerError();
		}
		return player;
	}
	
	public void logout(String userhost){
		Player player = Player.findPlayerByUserhost(userhost);
		if(player != null){
			player.setOnline(false);
			LOGGER.info(player.getName() + " has logged off.");
		}
	}
	
	public void register(String userhost, String playerName, String password) throws DuplicatePlayerError{
		if(null != Player.findPlayerByUserhost(userhost)){
			throw new DuplicatePlayerError();
		}
		
		Player player = new Player();
		player.setUserhost(userhost);
		player.setName(playerName);
		player.setOnline(true);
		
		player.persist();
		LOGGER.info("Player "+player.getName()+" registered.");
	}
	
	public Character createCharacter(String userhost, String avatarName, String designation) throws MustRegisterToCreateAvatarError{
		Player player = Player.findPlayerByUserhost(userhost);
		if(player != null){
			Character character = new Character();
			
			character.setCreated(new Date());
			character.setExperience(0);
			character.setLevel(1);
			character.setName(avatarName);
			character.setDesignation(designation);
			character.setLocation(new MapPoint(1, 1));
			character.setPlayer(player);
			
			LOGGER.debug("new avatar's player attrib set to "+player.getName()+". Result of calling getter: "+character.getPlayer());
			
			character.persist();
			return character;
		} else {
			throw new MustRegisterToCreateAvatarError();
		}
	}
	
}
