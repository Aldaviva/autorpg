package com.aldaviva.autorpg.data.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import com.aldaviva.autorpg.data.persistence.enums.ItemArticle;
import com.aldaviva.autorpg.data.persistence.enums.ItemSlot;

@RooJavaBean
@RooToString
@Entity
@RooEntity(identifierField = "name", identifierType = String.class)
public class Item {

    @Id
    private String name;

    @NotNull
    private Boolean rare;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemSlot slot;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "items")
    private Set<com.aldaviva.autorpg.data.entities.Character> characters = new HashSet<com.aldaviva.autorpg.data.entities.Character>();

    @Enumerated(EnumType.STRING)
    private ItemArticle article;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Item findItemByRandom() {
        EntityManager em = entityManager();
        TypedQuery<Item> q = em.createQuery("SELECT Item FROM Item AS item ORDER BY random()", Item.class);
        q.setMaxResults(1);
        return q.getSingleResult();
    }

    public static Item findItemByRandomUnique(boolean unique) {
        EntityManager em = entityManager();
        TypedQuery<Item> q = em.createQuery("SELECT Item FROM Item AS item WHERE item.rare = :rare ORDER BY random()", Item.class);
        q.setMaxResults(1);
        q.setParameter("rare", unique);
        return q.getSingleResult();
    }

    public static List<Item> findItemsByCharacterAndSlot(Character character, ItemSlot slot) {
        if (character == null) throw new IllegalArgumentException("The character argument is required");
        if (slot == null) throw new IllegalArgumentException("The slot argument is required");
        EntityManager em = entityManager();
        TypedQuery<Item> q = em.createQuery("SELECT Item FROM Item AS item WHERE item.slot = :slot AND :character MEMBER OF item.characters", Item.class);
        q.setParameter("slot", slot);
        q.setParameter("character", character);
        return q.getResultList();
    }
}
