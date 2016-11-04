/**
 * Programmed by BenlongHuang
 * Java Class Name: Wumpus.java
 * 
 * Behavior: This class is a Wumpus object, it has the ability to generate a random coordinate
 * of the wumpus's location. And has the ability to provide the x and the y
 * 
 * 
 **/
package model;

import java.util.Random;

public class Wumpus {
	
	private int X;
	private int Y;
	
	public Wumpus(Random random){
		X=  random.nextInt(10);
		Y=  random.nextInt(10);
	}

	
	public int getX(){
		return X;
	}
	
	public int getY(){
		return Y;
	}

}
