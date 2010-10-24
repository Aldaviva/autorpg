package com.aldaviva.autorpg.data.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

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
    
    public static String getValue(ConfigurationKey key){
    	return Configuration.findConfiguration(key).getValue();
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
}
