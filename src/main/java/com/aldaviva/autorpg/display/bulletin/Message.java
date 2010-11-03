package com.aldaviva.autorpg.display.bulletin;

public enum Message {
	
	CHARACTER_FOUND_ITEM(Style.CHARACTER_NAME + "${character.name}" + Style.NORMAL + " has found ${itemStyle} ${item.article} + ${item.name}" + Style.NORMAL + "."),
	HANDOFGOD_REWARD("${player} ${operator} ${reward} experience."),
	CHARACTER_LEVELS_UP("${character.name} has reached Level ${character.level}.");
	
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