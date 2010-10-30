package com.aldaviva.autorpg.game;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aldaviva.autorpg.AutoRPGException.DuplicatePlayerError;
import com.aldaviva.autorpg.AutoRPGException.LoginFailedBadPasswordError;
import com.aldaviva.autorpg.AutoRPGException.LoginFailedNoSuchPlayerError;
import com.aldaviva.autorpg.AutoRPGException.MustRegisterToCreateAvatarError;
import com.aldaviva.autorpg.data.entities.Character;
import com.aldaviva.autorpg.data.entities.Player;
import com.aldaviva.autorpg.data.persistence.types.MapPoint;

@Component
public class PlayerManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerManager.class);

	public Player login(String playerName, String plainPassword, String userhost) throws LoginFailedNoSuchPlayerError, LoginFailedBadPasswordError{
		Player player = Player.findPlayer(playerName);
		if(player != null){
			String providedHashedPassword = hash(player.getName()+plainPassword);
			if(player.getPassword().equals(providedHashedPassword)){
				player.setOnline(true);
				player.setUserhost(userhost);
				LOGGER.info("Login by "+player.getName());
			} else {
				LOGGER.info("Failed login by player "+player.getName()+".");
				throw new LoginFailedBadPasswordError();
			}
		} else {
			throw new LoginFailedNoSuchPlayerError();
		}
		return player;
	}
	
	public boolean logout(String userhost){
		Player player = Player.findByOnlineUserhost(userhost);
		if(player != null){
			player.setOnline(false);
			LOGGER.info(player.getName() + " has logged off.");
			return true;
		}
		return false;
	}
	
	public void register(String userhost, String playerName, String password) throws DuplicatePlayerError{
		if(null != Player.findByUserhost(userhost)){
			throw new DuplicatePlayerError();
		}
		
		Player player = new Player();
		player.setUserhost(userhost);
		player.setName(playerName);
		player.setOnline(true);
		
		String hashedPassword = hash(playerName+password);
		player.setPassword(hashedPassword);
		
		player.persist();
		LOGGER.info("Player "+player.getName()+" registered.");
	}
	
	public Character createCharacter(String userhost, String avatarName, String designation) throws MustRegisterToCreateAvatarError{
		Player player = Player.findByUserhost(userhost);
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
	
	public static String hash(String plain){
		return DigestUtils.sha256Hex(plain);
	}
	
	@Transactional
	public void enforcePlayersOnlineState(List<String> userhostsInChannel){
		LOGGER.info("Enforcing Players' online state.");
		
		List<Player> playersToSetOffline = Player.findByOnline();
		
		for (String userhost : userhostsInChannel) {
			Player currentlyOnlinePlayer = Player.findByOnlineUserhost(userhost);
			if(currentlyOnlinePlayer != null){
				LOGGER.info(currentlyOnlinePlayer.getName() + " automatically set to online after bot reconnection.");
				playersToSetOffline.remove(currentlyOnlinePlayer);
			}
		}
		
		for (Player player : playersToSetOffline) {
			LOGGER.info(player.getName() + " set to offline after bot reconnection.");
			player.setOnline(false);
		}
	}
	
}
