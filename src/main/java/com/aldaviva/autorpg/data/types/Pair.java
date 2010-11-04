package com.aldaviva.autorpg.data.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pair<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public T x;
	public T y;
	
	public Pair(T x, T y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Pair<T> clone(){
		return new Pair<T>(x, y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		} else {
			@SuppressWarnings("unchecked")
			Pair<T> other = (Pair<T>) obj;
			return x == other.x && y == other.y;
		}
	}

	public List<T> getList() {
		List<T> result = new ArrayList<T>();
		result.add(x);
		result.add(y);
		return result;
	}
	
	public void setList(List<T> list){
		if(list.size() != 2){
			throw new IllegalArgumentException("setList: Input list must have exactly two items, "+list.size()+" given.");
		}
		x = list.get(0);
		y = list.get(1);
	}
	
	public void set(int index, T item){
		if(index == 0){
			x = item;
		} else if(index == 1){
			y = item;
		} else {
			throw new IllegalArgumentException("setPropertyValue: Illegal index "+index+", only 0 and 1 allowed");
		}
	}
	
	public T get(int index){
		if(index == 0){
			return x;
		} else if(index == 1){
			return y;
		} else {
			throw new IllegalArgumentException("setPropertyValue: Illegal index "+index+", only 0 and 1 allowed");
		}
	}
	
}
