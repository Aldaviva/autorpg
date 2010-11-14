package com.aldaviva.autorpg.data.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.aldaviva.autorpg.Utils;
import com.aldaviva.autorpg.data.enums.ConfigurationKey;

@RooJavaBean
@RooToString
@Entity
@RooEntity
public class Handofgod implements Serializable {

    private static final long serialVersionUID = 1L;

	@NotNull
    private String description;

    @NotNull
    private Boolean beneficial;
    
    @Transient
    public int getReward(int experience){
    	double max_mult = Double.parseDouble(Configuration.getValue(ConfigurationKey.HAND_OF_GOD_EXP_MULTIPLIER_MAX));
    	double min_mult = Double.parseDouble(Configuration.getValue(ConfigurationKey.HAND_OF_GOD_EXP_MULTIPLIER_MIN));
    	return (beneficial ? 1 : -1) * Utils.getRandomInt((int) (min_mult * experience), (int) (max_mult* experience));
    }

    public static Handofgod findRandom() {
        EntityManager em = entityManager();
        TypedQuery<Handofgod> q = em.createQuery("SELECT Handofgod FROM Handofgod AS handofgod ORDER BY random()", Handofgod.class);
        q.setMaxResults(1);
        return q.getSingleResult();
    }
    
    public static Handofgod findRandomByBeneficial(boolean beneficial){
    	 EntityManager em = entityManager();
         TypedQuery<Handofgod> q = em.createQuery("SELECT Handofgod FROM Handofgod AS handofgod WHERE handofgod.beneficial = :beneficial ORDER BY random()", Handofgod.class);
         q.setParameter("beneficial", beneficial);
         q.setMaxResults(1);
         return q.getSingleResult();
    }
}
