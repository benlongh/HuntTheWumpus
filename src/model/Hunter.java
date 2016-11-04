/**
 * Programmed by BenlongHuang  
 * Java Class Name: Hunter.java
 * 
 * Behavior: This class is a Hunter object, it has the ability to generate a random coordinate
 * of the hunter's location. And has the ability to shoot and move and keep track of use of arrow
 * 
 * 
 **/
package model;

import java.util.Random;

public class Hunter {
	public int X;
	public int Y;
	private HuntTheWumpusGame theGame;
	private char pre;
	private boolean arrow;
 
	public Hunter(HuntTheWumpusGame game, Random random) {
		theGame = game;
		arrow = false;
		X = random.nextInt(10);
		Y = random.nextInt(10);
		pre = ' ';
		while (!theGame.checkSpace(X, Y)) {
			X = random.nextInt(10);
			Y = random.nextInt(10);
		}
	}

	/*
	 * The following four method is to make the move on the board 
	 * and store what's in the room also. 
	 * 
	 * */
	public void moveUp() {
		theGame.actualBoard[X][Y] = pre;
		theGame.userBoard[X][Y] = pre;

		pre = theGame.getElement(theGame.makeWrapAround(X - 1), Y);
		theGame.setVisible(theGame.makeWrapAround(X - 1), Y);
		X = theGame.makeWrapAround(X - 1);

		theGame.actualBoard[X][Y] = 'O';
		theGame.userBoard[X][Y] = 'O';
	}

	public void moveDown() {
		theGame.actualBoard[X][Y] = pre;
		theGame.userBoard[X][Y] = pre;

		pre = theGame.getElement(theGame.makeWrapAround(X + 1), Y);

		X = theGame.makeWrapAround(X + 1);

		theGame.actualBoard[X][Y] = 'O';
		theGame.userBoard[X][Y] = 'O';
	}

	public void moveLeft() {
		theGame.actualBoard[X][Y] = pre;
		theGame.userBoard[X][Y] = pre;

		pre = theGame.getElement(X, theGame.makeWrapAround(Y - 1));

		Y = theGame.makeWrapAround(Y - 1);

		theGame.actualBoard[X][Y] = 'O';
		theGame.userBoard[X][Y] = 'O';
	}

	public void moveRight() {
		theGame.actualBoard[X][Y] = pre;
		theGame.userBoard[X][Y] = pre;

		pre = theGame.getElement(X, theGame.makeWrapAround(Y + 1));

		Y = theGame.makeWrapAround(Y + 1);

		theGame.actualBoard[X][Y] = 'O';
		theGame.userBoard[X][Y] = 'O';
	}

	public char getWhatsInTheRoom() {
		return pre;
	}
	
	/*
	 * THe following two methods are to make the shooting action
	 * and let the game end and check if the hunter got the target or not
	 * */

	public void shootUpOrDown() { 
		// x direction
		boolean gotTarget = false;
		for (int i = 0; i < theGame.getSize(); i++) {
			if(theGame.actualBoard[i][Y] == 'W'){
				gotTarget = true;
				break;
			}
		}
		theGame.makeWinOrLose(gotTarget);
		arrow = true;
	}
	
	
	public void shootLeftOrRight() {
		// x direction
		boolean gotTarget = false;
		for (int i = 0; i < theGame.getSize(); i++) {
			if(theGame.actualBoard[X][i] == 'W'){
				gotTarget = true;
				break;
			}
		}
		theGame.makeWinOrLose(gotTarget);
		arrow = true;
	}
	
	// keep track of the use of the arrow
	public boolean getArrow(){
		return arrow;
	}
	
}
