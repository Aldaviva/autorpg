package com.aldaviva.autorpg.data.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.aldaviva.autorpg.AutoRPGException;
import com.aldaviva.autorpg.AutoRPGException.NotEnoughPlayersError;
import com.aldaviva.autorpg.data.persistence.types.MapPoint;

@RooJavaBean
@RooToString
@Entity
@RooEntity(finders = { "findCharactersByPlayer" })
public class Character {

    @Id
    private String name;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Date created;

    @NotNull
    private Integer experience;

    @NotNull
    private String designation;

    @ManyToOne
    private Player player;

    private Integer level;

    private MapPoint location;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Item> items = new HashSet<Item>();

    @NotNull
    private Boolean female;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getPossessivePronoun(){
    	if(getFemale()){
    		return "her";
    	} else {
    		return "his";
    	}
    }

    public static List<Character> findOnlinePlayersByRandom(int number) throws NotEnoughPlayersError {
        EntityManager em = entityManager();
        TypedQuery<Character> q = em.createQuery("SELECT Character FROM Character AS character WHERE character.player.online = true ORDER BY random()", Character.class);
        q.setMaxResults(number);
        List<Character> resultList = q.getResultList();
        if (resultList.size() < number) {
            throw new AutoRPGException.NotEnoughPlayersError();
        }
        return resultList;
    }
    
}