package com.aldaviva.autorpg.display.bulletin;

import java.util.ArrayList;
import java.util.List;

import org.jibble.pircbot.Colors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aldaviva.autorpg.AutoRPGException.ConfigurationIncompleteError;
import com.aldaviva.autorpg.display.irc.Bot;
import com.aldaviva.autorpg.display.twitter.TwitterBulletinHandler;

@Component
public class BulletinManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(BulletinManager.class);
	
	private List<BulletinHandler> bulletinHandlers = new ArrayList<BulletinHandler>();
	
	@Autowired(required = false)
	private Bot bot;
	
	public void init(){
		LOGGER.debug("Initializing Bulletin Manager");
		try {
			
			bulletinHandlers.add(new LoggerBulletinHandler());
			if(bot != null){
				bulletinHandlers.add(bot);
			}
			bulletinHandlers.add(new TwitterBulletinHandler());
			
		} catch (ConfigurationIncompleteError e){
			LOGGER.warn(e.getMessage());
		}
	}
	
	public void publish(Bulletin bulletin){
		publish(bulletin, true);
	}
	
	public void publish(Bulletin bulletin, boolean publishToTwitter){
		if(bulletin != null && !bulletin.isEmpty()){
			for (BulletinHandler bulletinHandler : bulletinHandlers) {
				if(publishToTwitter || !bulletinHandler.getClass().equals(TwitterBulletinHandler.class)){
					bulletinHandler.handleBulletin(bulletin);
				}
			}
		}
	}
	
	private class LoggerBulletinHandler implements BulletinHandler {

		private final Logger LOGGER = LoggerFactory.getLogger(Bulletin.class);
		
		@Override
		public void handleBulletin(Bulletin bulletin) {
			LOGGER.info(bulletin.toStringStripFormatting());
		}
		
	}
	
	public static void main(String[] args){
		String formattedMessage = Colors.BOLD+"a";
		Bulletin formattedBulletin = new Bulletin();
		formattedBulletin.add(formattedMessage);
		System.out.println("formatted: "+formattedBulletin.toString());
		System.out.println("unformatted: "+formattedBulletin.toStringStripFormatting());
	}
	
}
