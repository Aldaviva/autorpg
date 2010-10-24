// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.aldaviva.autorpg.data.entities;

import com.aldaviva.autorpg.data.entities.Item;
import com.aldaviva.autorpg.data.entities.Player;
import com.aldaviva.autorpg.data.persistence.types.MapPoint;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import java.util.Set;

privileged aspect Character_Roo_JavaBean {
    
    public Date Character.getCreated() {
        return this.created;
    }
    
    public void Character.setCreated(Date created) {
        this.created = created;
    }
    
    public Integer Character.getExperience() {
        return this.experience;
    }
    
    public void Character.setExperience(Integer experience) {
        this.experience = experience;
    }
    
    public String Character.getDesignation() {
        return this.designation;
    }
    
    public void Character.setDesignation(String designation) {
        this.designation = designation;
    }
    
    public Player Character.getPlayer() {
        return this.player;
    }
    
    public void Character.setPlayer(Player player) {
        this.player = player;
    }
    
    public Integer Character.getLevel() {
        return this.level;
    }
    
    public void Character.setLevel(Integer level) {
        this.level = level;
    }
    
    public MapPoint Character.getLocation() {
        return this.location;
    }
    
    public void Character.setLocation(MapPoint location) {
        this.location = location;
    }
    
    public Set<Item> Character.getItems() {
        return this.items;
    }
    
    public void Character.setItems(Set<Item> items) {
        this.items = items;
    }
    
    public Boolean Character.getFemale() {
        return this.female;
    }
    
    public void Character.setFemale(Boolean female) {
        this.female = female;
    }
    
}
