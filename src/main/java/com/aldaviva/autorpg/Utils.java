package com.aldaviva.autorpg;

import java.util.List;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {

	private Utils(){};
	
	private static Random prng = new Random();
	
	public static String hash(String plain){
		return DigestUtils.sha256Hex(plain);
	}
	
	public static String commaAndList(List<String> list){
		String result = "";
		int numberOfCharacters = list.size();
		switch(numberOfCharacters){
		case 1:
			result = list.get(0);
			break;
		case 2:
			result = list.get(0)+" and "+list.get(1);
			break;
		default:
			StringBuilder nameList = new StringBuilder();
			for (int i = 0; i < numberOfCharacters; i++) {
				if(i > 0){
					nameList.append(", ");
				}
				if(i == numberOfCharacters - 1){
					nameList.append("and ");
				}
				nameList.append(list.get(i));
			}
			result = nameList.toString();
		}
		return result;
	}

	public static int getRandomInt(int lowerInclusive, int upperInclusive) {
		if (upperInclusive < lowerInclusive) {
			throw new IllegalArgumentException("Arguments in wrong order");
		}
	
		int range = upperInclusive - lowerInclusive + 1; // both side are inclusive
		int random = prng.nextInt(range);
		random += lowerInclusive;
		return random;
	}
	
}
