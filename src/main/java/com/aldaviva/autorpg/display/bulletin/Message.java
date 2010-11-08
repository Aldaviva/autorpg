package com.aldaviva.autorpg.display.bulletin;

public enum Message {
	
	CHARACTER_FOUND_ITEM(Style.CHARACTER_NAME + "${character.name}" + Style.NORMAL + " has found ${item.article}${itemStyle}${item.name}" + Style.NORMAL + "."),
	HANDOFGOD_REWARD("${pronoun} experience ${operator} ${reward}."),
	CHARACTER_LEVELS_UP(Style.CHARACTER_NAME + "${character.name}"+Style.NORMAL +" has reached Level ${character.level}."),
	QUEST_HAS_CHARACTERS(Style.CHARACTER_NAME + "${characterA}" + Style.NORMAL+", "+Style.CHARACTER_NAME + "${characterB}" + Style.NORMAL+", "+Style.CHARACTER_NAME + "${characterC}" + Style.NORMAL+" and "+Style.CHARACTER_NAME + "${characterD}" + Style.NORMAL+" are on a quest.");
	
	private String message;
	
	private Message(final String message){
		this.message = message;
	}
	
	public String fillIn(String... replacementPairs){
		if(replacementPairs.length % 2 != 0){
			throw new IllegalArgumentException("An even number of arguments is required.");
		}
		
		String result = message;
		for(int argNum=0; argNum < replacementPairs.length; argNum += 2){
			result = result.replace("${"+replacementPairs[argNum]+"}", replacementPairs[argNum+1]);
		}
		
		return result;
	}
}