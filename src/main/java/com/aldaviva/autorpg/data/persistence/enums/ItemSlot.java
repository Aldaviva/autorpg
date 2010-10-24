package com.aldaviva.autorpg.data.persistence.enums;

public enum ItemSlot {
	HEAD("on"),
	HAND("in", 2),
	BELT("on"),
	TORSO("around"),
	FEET("on"),
	LEGS("on"),
	AMULET("as");

	private int slotsPerCharacter;
	private String preposition;

	private ItemSlot(String preposition) {
		this(preposition, 1);
	}

	private ItemSlot(String preposition, int slotsPerCharacter) {
		this.preposition = preposition;
		this.slotsPerCharacter = slotsPerCharacter;
	}

	public int getSlotsPerCharacter() {
		return slotsPerCharacter;
	}
	
	public String getPreposition(){
		return preposition;
	}
}
