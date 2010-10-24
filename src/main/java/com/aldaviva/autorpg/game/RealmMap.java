package com.aldaviva.autorpg.game;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.aldaviva.autorpg.data.entities.Configuration;
import com.aldaviva.autorpg.data.persistence.enums.ConfigurationKey;
import com.aldaviva.autorpg.data.persistence.types.MapPoint;

@Component
public class RealmMap {

	private static final Logger LOGGER = LoggerFactory.getLogger(RealmMap.class);
	
	public void init(){
		LOGGER.info("Initializing World Map.");
	}
	
	public MapPoint getDestination(MapPoint origin, MovementDirection movement) {
		MapPoint destination = new MapPoint();

		int upperX = Integer.parseInt(Configuration.getValue(ConfigurationKey.MAP_WIDTH));
		int lowerX = 0;
		int upperY = Integer.parseInt(Configuration.getValue(ConfigurationKey.MAP_HEIGHT));
		int lowerY = 0;
		
		destination.x = Math.min(upperX, Math.max(lowerX, origin.x + movement.getDeltaX()));
		destination.y = Math.min(upperY, Math.max(lowerY, origin.y + movement.getDeltaY()));

		return destination;
	}
	
	public enum MovementDirection {
		NORTH(0, 1), SOUTH(0, -1), EAST(1, 0), WEST(-1, 0), NORTHEAST(1, 1), NORTHWEST(-1, 1), SOUTHEAST(1, -1), SOUTHWEST(-1, -1);
		
		private int deltaX;
		private int deltaY;
		
		private MovementDirection(int deltaX, int deltaY) {
			this.deltaX = deltaX;
			this.deltaY = deltaY;
		}
		
		public static MovementDirection getRandomDirection(){
			int ord = new Random().nextInt(values().length);
			return values()[ord];
		}
		
		public int getDeltaX() {
			return deltaX;
		}
		
		public int getDeltaY() {
			return deltaY;
		}
	}
}