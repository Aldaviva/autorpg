package com.aldaviva.autorpg.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GameState {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameState.class);
	
	@Autowired
	public ProgressUpdater progressUpdater;
	
	@Autowired
	public RealmMap worldMap;
	
	@Autowired
	private RandomEventManager randomEventManager;

	private static Integer tickInterval; //seconds


	public void init(){
		LOGGER.info("Initializing Game State.");
		
		//setUpDefaults();
	}
	
	/*private void setUpDefaults() {
		for (ConfigurationKey key: ConfigurationKey.values()){
			if(Configuration.findConfiguration(key) == null){
				Configuration config = new Configuration();
				config.setKey(key);
				config.setToDefaultValue();
				config.persist();
			}
		}
	}*/

	public static Integer getTickInterval() {
		return tickInterval;
	}
	
	public void setTickInterval(Integer tick_interval_millis) {
		tickInterval = tick_interval_millis / 1000;
	}
	
	public void update(){
		progressUpdater.update();
		randomEventManager.update();
	}

}
