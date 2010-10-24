package com.aldaviva.autorpg.display.irc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public enum PlayerAction {

	REGISTER(Usage.REGISTER),
	LOGIN(null),
	LOGOUT(null),
	CREATE(Usage.CREATE),
	SET(null),
	GET(null),
	CONFIG(null),
	CHARACTER(Usage.CHARACTER),
	FINDITEM(null, true);
	
	private Usage usage;
	private boolean cheat = false;
	
	private PlayerAction(Usage usage){
		this.usage = usage;
	}
	
	private PlayerAction(Usage usage, boolean cheat){
		this(usage);
		this.cheat = cheat;
	}
	
	public Usage getUsage() {
		return usage;
	}
	
	static final Map<String, PlayerAction> map = new HashMap<String, PlayerAction>();
	
	static {
		for(PlayerAction action : values()){
			map.put(action.toString(), action);
		}
	}
	
	public static PlayerAction getByName(String name){
		PlayerAction result = map.get(name.toUpperCase());
		return result;
	}
	
	public static List<PlayerAction> valuesWithoutCheats(){
		ArrayList<PlayerAction> valuesWithoutCheats = new ArrayList<PlayerAction>(Arrays.asList(values()));
		Iterator<PlayerAction> iter = valuesWithoutCheats.iterator();
		while(iter.hasNext()){
			PlayerAction playerAction = iter.next();
			if(playerAction.cheat){
				iter.remove();
			}
		}
		return valuesWithoutCheats;
	}
}
