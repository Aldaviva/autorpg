package com.aldaviva.autorpg.display.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.Bulletin;
import com.aldaviva.autorpg.display.BulletinHandler;

public class TwitterBulletinHandler implements BulletinHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterBulletinHandler.class);

	private final Twitter twitter;

	public TwitterBulletinHandler() {
		twitter = new TwitterFactory().getInstance(Configuration.getValue(ConfigurationKey.TWITTER_USERNAME),
				Configuration.getValue(ConfigurationKey.TWITTER_PASSWORD));
	}

	@Override
	public void handle(Bulletin bulletin) {
		try {
			twitter.updateStatus(bulletin.toString());
		} catch (TwitterException e) {
			LOGGER.error(e.getMessage());
		}
	}

}
