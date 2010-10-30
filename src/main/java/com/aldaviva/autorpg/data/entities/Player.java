package com.aldaviva.autorpg.data.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@Entity
@RooEntity(finders = { "findPlayersByUserhost" })
public class Player {

    @Id
    private String name;

    @Column(unique = true)
    private String userhost;

    @NotNull
    private Boolean online;

    @NotNull
    private String password;

    @NotNull
    private Boolean superuser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Player findByUserhost(String userhost) {
        try {
            return findPlayersByUserhost(userhost).getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public static List<Player> findByOnline() {
        EntityManager em = entityManager();
        TypedQuery<Player> q = em.createQuery("SELECT Player FROM Player AS player WHERE player.online = :online", Player.class);
        q.setParameter("online", true);
        return q.getResultList();
    }

    public static Player findByOnlineAndUserhost(String userhost) {
        Player player = findByUserhost(userhost);
        if (player != null && player.getOnline()) {
            return player;
        }
        return null;
    }
}
