package com.aldaviva.autorpg.data.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
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
import com.aldaviva.autorpg.data.types.MapPoint;
import com.aldaviva.autorpg.data.entities.Quest;
import com.aldaviva.autorpg.data.enums.ConfigurationKey;

@RooJavaBean
@RooToString
@Entity
@RooEntity(finders = { "findCharactersByPlayer" })
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @NotNull
    private Integer level;

    private MapPoint location;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Item> items = new HashSet<Item>();

    @NotNull
    private Boolean female;

    @ManyToOne
    private Quest quest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPossessivePronoun() {
        if (getFemale()) {
            return "her";
        } else {
            return "his";
        }
    }
    
    public static List<Character> findAllCharactersOrderByOnlineAndExperience(){
    	 EntityManager em = entityManager();
         TypedQuery<Character> q = em.createQuery("SELECT Character FROM Character AS character ORDER BY character.player.online DESC, character.level DESC, character.experience DESC", Character.class);
         return q.getResultList();
    }

    public static List<Character> findRandomByOnline(int number) throws NotEnoughPlayersError {
        EntityManager em = entityManager();
        TypedQuery<Character> q = em.createQuery("SELECT Character FROM Character AS character WHERE character.player.online = true ORDER BY random()", Character.class);
        q.setMaxResults(number);
        List<Character> resultList = q.getResultList();
        if (resultList.size() < number) {
            throw new AutoRPGException.NotEnoughPlayersError();
        }
        return resultList;
    }
    
    /**
	 * This may be different from getLevel() if a level was just gained
	 * You can never lose a level
	 */
	public int calculateLevelFromExperience() {
		return Math.max(getLevel(), 1 + (int) Math.floor(getMult() * Math.pow(getExperience(), getPower())));
	}
	
	public int calculateExperienceFromLevel(int goalLevel){
		return (int) Math.ceil(Math.pow(goalLevel/getMult(), 1/getPower()));
	}
	
	public double getProgressTowardsNextLevel(){
		int experience = getExperience();
		int goalExperience = calculateExperienceFromLevel(getLevel()+1);
		return (double) experience/goalExperience;
	}

	private double getMult() {
		return getLevelCap()/Math.pow(getSecondsToLevelCap(), getPower());
	}

	private double getPower() {
		return Double.parseDouble(Configuration.getValue(ConfigurationKey.LEVEL_CURVE));
	}

	private int getSecondsToLevelCap() {
		return Integer.parseInt(Configuration.getValue(ConfigurationKey.SECONDS_TO_LEVEL_CAP));
	}
	
	private int getLevelCap(){
		return Integer.parseInt(Configuration.getValue(ConfigurationKey.LEVEL_CAP));
	}
}
