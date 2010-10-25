package com.aldaviva.autorpg.display.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

import com.aldaviva.autorpg.RehashListener;
import com.aldaviva.autorpg.AutoRPGException.ConfigurationIncompleteError;
import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.display.Bulletin;
import com.aldaviva.autorpg.display.BulletinHandler;

public class TwitterBulletinHandler implements BulletinHandler, RehashListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterBulletinHandler.class);

	private Twitter twitter;

	public TwitterBulletinHandler() throws ConfigurationIncompleteError {
		Configuration.addRehashListener(this);
		init();
	}
	
	@SuppressWarnings("deprecation")
	public void init(){
		twitter = new TwitterFactory().getInstance();
		
		String consumerKey = Configuration.getValue(ConfigurationKey.TWITTER_CONSUMER_KEY);
		String consumerSecret = Configuration.getValue(ConfigurationKey.TWITTER_CONSUMER_SECRET);
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		
		String token = Configuration.getValue(ConfigurationKey.TWITTER_OAUTH_TOKEN);
		String tokenSecret = Configuration.getValue(ConfigurationKey.TWITTER_OAUTH_TOKEN_SECRET);
		AccessToken accessToken = new AccessToken(token, tokenSecret);
		twitter.setOAuthAccessToken(accessToken);
	}

	@Override
	public void handle(Bulletin bulletin) {
		if(Boolean.parseBoolean(Configuration.getValue(ConfigurationKey.TWITTER_ENABLED))){
			try {
				twitter.updateStatus(bulletin.toString());
				LOGGER.debug("Updating status to \"" + bulletin.toString() + "\".");
			} catch (TwitterException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}

	@Override
	public void rehash() {
		init();
		LOGGER.info("Rehashed.");
	}

}
