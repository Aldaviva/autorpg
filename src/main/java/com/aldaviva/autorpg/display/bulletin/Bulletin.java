package com.aldaviva.autorpg.display.bulletin;

import org.jibble.pircbot.Colors;

public class Bulletin {
	
	private static final String STRIP_FORMATTING_REGEX = "["+Colors.BOLD+Colors.UNDERLINE+Colors.NORMAL+"]";
	
	private StringBuilder builder = new StringBuilder();
	
	public Bulletin(String str){
		builder.append(str);
	}
	
	public Bulletin(){
		
	}
	
	public void add(String str){
		builder.append(str);
	}
	
	public void addLine(String str){
		add("\n"+str);
	}
	
	public boolean isEmpty(){
		return builder.length() == 0;
	}
	
	public String toString(){
		return builder.toString();
	}
	
	public String toStringStripFormatting(){
		return toString().replaceAll(STRIP_FORMATTING_REGEX, "");
	}
	
	
}
