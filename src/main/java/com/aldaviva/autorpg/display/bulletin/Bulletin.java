package com.aldaviva.autorpg.display.bulletin;

import org.jibble.pircbot.Colors;

public class Bulletin {
	
	private StringBuilder builder = new StringBuilder();
	
	public Bulletin(String str){
		builder.append(str);
	}
	
	public Bulletin(){
		
	}
	
	public Bulletin(Message message){
		this(message.toString());
	}
	
	public void add(String str){
		if(builder.length() != 0){
			builder.append(" ");
		}
		builder.append(str);
	}
	
	public void add(Message message){
		add(message.toString());
	}
	
	public void addLine(String str){
		add("\n"+str);
	}
	
	public void add(Bulletin bulletin){
		addLine(bulletin.toString());
	}
	
	public boolean isEmpty(){
		return builder.length() == 0;
	}
	
	public String toString(){
		return builder.toString();
	}
	
	public String toStringStripFormatting(){
		return Colors.removeFormattingAndColors(toString());
	}
	
	
}
