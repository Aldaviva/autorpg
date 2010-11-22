package com.aldaviva.autorpg.data.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.aldaviva.autorpg.data.enums.RewardType;

@RooJavaBean
@RooToString
@Entity
@RooEntity(identifierField = "name", identifierType = java.lang.String.class)
public class Quest implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int STEPS = 3;

	public static final int CHARACTERS_PER_QUEST = 4;

	@Id
	private String name;

	@NotNull
	private Integer level;

	@NotNull
	private String mission;

	@NotNull
	private Integer expRemaining;

	@NotNull
	private Integer step;

	@NotNull
	@Enumerated(EnumType.STRING)
	private RewardType reward;

	@NotNull
	private String step1;

	@NotNull
	private String step1done;

	@NotNull
	private String step2;

	@NotNull
	private String step2done;

	@NotNull
	private String step3;

	@NotNull
	private String step3done;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "quest")
	private Set<com.aldaviva.autorpg.data.entities.Character> characters = new HashSet<Character>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Quest findRandomByInactive() {
		EntityManager em = entityManager();
		TypedQuery<Quest> q = em.createQuery("SELECT Quest FROM Quest AS quest WHERE step = 0 ORDER BY random()", Quest.class);
		q.setMaxResults(1);
		return q.getSingleResult();
	}

	public static List<Quest> findByActive() {
		EntityManager em = entityManager();
		TypedQuery<Quest> q = em.createQuery("SELECT Quest FROM Quest AS quest WHERE step != 0", Quest.class);
		return q.getResultList();
	}

	@Transient
	public int countOnlineCharacters() {
		return characters.size();
	}

	@Transient
	public int getExpTotal() {
		 return level * 24 * 60 * 60 * CHARACTERS_PER_QUEST * CHARACTERS_PER_QUEST;
//		return 64;
	}

	@Transient
	public int calculateCurrentStep() {
		return (int) Math.floor((STEPS * (1-((double) getExpRemaining() / getExpTotal()))) + 1);
	}
}
