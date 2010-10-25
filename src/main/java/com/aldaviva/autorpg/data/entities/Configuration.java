package com.aldaviva.autorpg.data.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import com.aldaviva.autorpg.RehashListener;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;

@RooJavaBean
@RooToString
@Entity
@RooEntity
public class Configuration {

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
    
    @Transactional
    public static String getValue(ConfigurationKey key){
    	Configuration configuration = Configuration.findConfiguration(key);
    	
    	if(configuration == null){
    		configuration = new Configuration();
    		configuration.setKey(key);
    		configuration.setToDefaultValue();
    		configuration.persist();
    		rehash();
    	}
    	
    	return configuration.getValue();
    }
    
    public void setToDefaultValue(){
    	if(key == null) throw new IllegalStateException("Key is null, can't set configuration to default value");
    	
    	setValue(key.getDefaultValue());
    }
    
    public void setToDefaultValueIfUnset(){
    	if(value == null){
    		setToDefaultValue();
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
