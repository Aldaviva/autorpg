package com.aldaviva.autorpg.data.types;

public class MapPoint extends Pair<Integer> {

	private static final long serialVersionUID = 1L;

	public MapPoint(){
		this(0,0);
	}
	
	public MapPoint(Integer x, Integer y) {
		super(x, y);
	}
	
	public MapPoint(Pair<Integer> pair){
		super(pair.x, pair.y);
	}
	
}
