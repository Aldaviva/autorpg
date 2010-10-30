package com.aldaviva.autorpg.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;

@Service
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
		
		setUpDefaults();
		
		checkConfiguration();
	}
	
	@Transactional
	private void setUpDefaults() {
		for (ConfigurationKey key: ConfigurationKey.values()){
			if(Configuration.findConfiguration(key) == null){
				Configuration config = new Configuration();
				config.setKey(key);
				config.setValue(key.getDefaultValue());
				config.persist();
				//LOGGER.info("Set config "+key+" = "+config.getValue());
			}
		}
	}
	
	private void checkConfiguration(){
		if(Configuration.countConfigurations() == 0){
			LOGGER.warn("There are no configurations.");
		} else {
			for(Configuration config : Configuration.findAllConfigurations()){
				LOGGER.info(config.getKey()+" = "+config.getValue());
			}
		}
	}

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
