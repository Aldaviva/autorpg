package com.aldaviva.autorpg.data.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.aldaviva.autorpg.RehashListener;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;

@RooJavaBean
@RooToString
@Entity
@RooEntity
public class Configuration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
	

    @Id
    @Enumerated(EnumType.STRING)
    private ConfigurationKey key;

    @NotNull
    private String value;

    public ConfigurationKey getKey() {
        return key;
    }

    public void setKey(ConfigurationKey key) {
        this.key = key;
    }
    
    public static String getValue(ConfigurationKey key){
    	LOGGER.debug("Asking for configuration value of "+key);
    	
    	Configuration configuration = Configuration.findConfiguration(key);
    	
    	/*if(configuration == null){
    		
    		LOGGER.debug("Config empty, setting to default value");
    		
    		configuration = new Configuration();
    		configuration.setKey(key);
    		configuration.setValue(key.getDefaultValue());
    		configuration.persist();
//    		rehash();
    	}*/
    	
    	if(configuration != null){
	    	LOGGER.debug("Returning "+configuration.getValue());
	    	return configuration.getValue();
    	} else {
    		return null;
    	}
    }
    
    @Transient
    private static List<RehashListener> rehashListeners = new ArrayList<RehashListener>();
    
    public static void addRehashListener(RehashListener rehashlistener){
    	rehashListeners.add(rehashlistener);
    }
    
    private static void rehash(){
    	 for (RehashListener rehashListener : rehashListeners) {
 			rehashListener.rehash();
 		}
    }
    
    public void setValue(String value) {
        this.value = value;
        
        rehash();
    }
}
