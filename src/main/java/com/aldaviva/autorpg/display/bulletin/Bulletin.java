package com.aldaviva.autorpg.display.bulletin;

public class Bulletin {

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
	
	public String toString(){
		return builder.toString();
	}
	
	
}
