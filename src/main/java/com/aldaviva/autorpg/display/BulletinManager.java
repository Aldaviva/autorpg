package com.aldaviva.autorpg.display;

import java.util.ArrayList;
import java.util.List;

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
	
	@Autowired
	private Bot bot;
	
	public void init(){
		LOGGER.info("Initializing Bulletin Manager");
		try {
			
			bulletinHandlers.add(new LoggerBulletinHandler());
			bulletinHandlers.add(bot);
			bulletinHandlers.add(new TwitterBulletinHandler());
			
		} catch (ConfigurationIncompleteError e){
			LOGGER.warn(e.getMessage());
		}
	}
	
	public void publish(Bulletin bulletin){
		for (BulletinHandler bulletinHandler : bulletinHandlers) {
			bulletinHandler.handleBulletin(bulletin);
		}
	}
	
	private class LoggerBulletinHandler implements BulletinHandler {

		private final Logger LOGGER = LoggerFactory.getLogger(Bulletin.class);
		
		@Override
		public void handleBulletin(Bulletin bulletin) {
			LOGGER.info(bulletin.toString());
		}
		
	}
	
}
