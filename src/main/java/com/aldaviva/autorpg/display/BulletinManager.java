package com.aldaviva.autorpg.display;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aldaviva.autorpg.display.irc.Bot;
import com.aldaviva.autorpg.display.twitter.TwitterBulletinHandler;

@Component
public class BulletinManager {

	private List<BulletinHandler> bulletinHandlers = new ArrayList<BulletinHandler>();
	
	@Autowired
	private Bot bot;
	
	public void init(){
		bulletinHandlers.add(new LoggerBulletinHandler());
		bulletinHandlers.add(bot);
		bulletinHandlers.add(new TwitterBulletinHandler());
	}
	
	public void publish(Bulletin bulletin){
		
	}
	
	private class LoggerBulletinHandler implements BulletinHandler {

		private final Logger LOGGER = LoggerFactory.getLogger("Bulletin");
		
		@Override
		public void handle(Bulletin bulletin) {
			LOGGER.info(bulletin.toString());
		}
		
	}
	
}
