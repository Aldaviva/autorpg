package com.aldaviva.autorpg.data.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import com.aldaviva.autorpg.data.enums.RewardType;

@RooJavaBean
@RooToString
@Entity
@RooEntity(identifierField = "name", identifierType=java.lang.String.class)
public class Quest {

    @Id
    private String name;

    @NotNull
    private Integer level;

    @NotNull
    private String mission;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    private RewardType reward;

    @OneToMany(mappedBy="quest")
    private Set<Character> characters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
