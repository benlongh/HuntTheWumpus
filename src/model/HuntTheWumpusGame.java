/**
 * Programmed by BenlongHuang
 * Java Class Name: HuntTheWumpusGame.java
 * 
 * Behavior: This class extends the Java's Observable and provide the game's main logic
 * it has several methods to make the game playable. It has the main character -- theWUmpus and theHunter
 * and has the boolean values to determine if the game is over or not
 * and has the ability to make the hunter move and shoot.
 * 
 * 
 **/
package model;

import java.util.Observable;
import java.util.Random; 

/**
 * Symbols used in the text view above: [O] Hunter [X] Hidden Room (not yet
 * visited) [S] Slime [B] Blood [G] Goop (blood and slime in the same room) [W]
 * Wumpus [P] Pit [ ] Visited room with nothing in it
 */

public class HuntTheWumpusGame extends Observable {
	public char[][] userBoard;
	public char[][] actualBoard;
	private int size;
	private Wumpus theWumpus;
	private Hunter theHunter;
	private boolean isGameEnd; 
	private boolean winOrLose;
 
	public HuntTheWumpusGame(Random random) {
		size = 10;
		isGameEnd = false;
		initializeBoard(random);
	}
	
	
	private void initializeBoard(Random random) {
		userBoard = new char[size][size];
		actualBoard = new char[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				actualBoard[i][j] = ' ';
				userBoard[i][j] = 'X';
			}
		}
		theWumpus = new Wumpus(random);
		setUpWumpusAndBlood();
		setUpPitsAndSlime(random);
		theHunter = new Hunter(this, random);
		actualBoard[theHunter.X][theHunter.Y] = 'O';
		userBoard[theHunter.X][theHunter.Y] = 'O';

	}

	/*
	 * Once we got the coordinate of the wumpus,we can set up the blood manually
	 * it's not the perfect way to do it but it is the easist way
	 * */
	private void setUpWumpusAndBlood() {
		actualBoard[theWumpus.getX()][theWumpus.getY()] = 'W';

		actualBoard[makeWrapAround(theWumpus.getX())][makeWrapAround(theWumpus.getY() - 2)] = 'B';
		actualBoard[makeWrapAround(theWumpus.getX())][makeWrapAround(theWumpus.getY() - 1)] = 'B';
		actualBoard[makeWrapAround(theWumpus.getX())][makeWrapAround(theWumpus.getY() + 1)] = 'B';
		actualBoard[makeWrapAround(theWumpus.getX())][makeWrapAround(theWumpus.getY() + 2)] = 'B';

		actualBoard[makeWrapAround(theWumpus.getX() - 2)][makeWrapAround(theWumpus.getY())] = 'B';
		actualBoard[makeWrapAround(theWumpus.getX() - 1)][makeWrapAround(theWumpus.getY())] = 'B';
		actualBoard[makeWrapAround(theWumpus.getX() + 1)][makeWrapAround(theWumpus.getY())] = 'B';
		actualBoard[makeWrapAround(theWumpus.getX() + 2)][makeWrapAround(theWumpus.getY())] = 'B';

		actualBoard[makeWrapAround(theWumpus.getX() - 1)][makeWrapAround(theWumpus.getY() - 1)] = 'B';
		actualBoard[makeWrapAround(theWumpus.getX() + 1)][makeWrapAround(theWumpus.getY() + 1)] = 'B';
		actualBoard[makeWrapAround(theWumpus.getX() - 1)][makeWrapAround(theWumpus.getY() + 1)] = 'B';
		actualBoard[makeWrapAround(theWumpus.getX() + 1)][makeWrapAround(theWumpus.getY() - 1)] = 'B';
	}

	/*
	 * After the setting up of wumpus and blood. We set up the pits and slime
	 * generate a random value from 3 to 5(both inclusive) and loop those times to make the 
	 * number of pits randomly.
	 * */
	private void setUpPitsAndSlime(Random random) {
		int num = random.nextInt(3) + 3;
		int x;
		int y;
		for (int i = 0; i < num; i++) {
			x = random.nextInt(10);
			y = random.nextInt(10);
			while (!checkSpace(x, y)) {
				x = random.nextInt(10);
				y = random.nextInt(10);
			}
			actualBoard[x][y] = 'P';
			actualBoard[makeWrapAround(x + 1)][y] = checkBlood(makeWrapAround(x + 1), y);
			actualBoard[makeWrapAround(x - 1)][y] = checkBlood(makeWrapAround(x - 1), y);
			actualBoard[x][makeWrapAround(y + 1)] = checkBlood(x, makeWrapAround(y + 1));
			actualBoard[x][makeWrapAround(y - 1)] = checkBlood(x, makeWrapAround(y - 1));
		}
	}

	/*
	 * While setting up, we should keep track of the current space we generate from the random generator
	 * */
	private char checkBlood(int x, int y) {
		if (actualBoard[x][y] == 'B')
			return 'G';
		return 'S';
	}

	public boolean checkSpace(int x, int y) {
		if (actualBoard[x][y] == ' ')
			return true;
		return false;
	}

	/*
	 * this is the tostring method for the player's perspective(which is X all over the board at the beginning)
	 * */
	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result += "[" + userBoard[i][j] + "]  ";
			}
			result += "\n";
			result += "\n";
		}
		return result;
	} 

	/*
	 * this is the toString method for the acutal board when the game is over
	 * */
	public String toStringEnd() {
		String result = "";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result += "[" + actualBoard[i][j] + "]  ";
			}
			result += "\n";
			result += "\n";
		}
		return result;
	}

	/*
	 * check if the coordinate out of bound or not and make it wrap around-able
	 * */
	public int makeWrapAround(int coordinate) {
		if (coordinate > 9)
			return coordinate - 10;
		if (coordinate < 0)
			return 10 + coordinate;
		return coordinate;
	}

	public char getElement(int x, int y) {
		return actualBoard[x][y];
	}

	public void setVisible(int x, int y) {
		userBoard[x][y] = actualBoard[x][y];
	}

	public char getElement() {
		return theHunter.getWhatsInTheRoom();
	}

	public void endGame() {
		isGameEnd = true;
	}

	public boolean isGameEnd() {
		return isGameEnd;
	}

	public void makeWinOrLose(boolean result) {
		winOrLose = result;
		endGame();
	}

	public boolean getWinOrLose() {
		return winOrLose;
	}

	public int getSize() {
		return size;
	}

	public void makeMoveUp() {
		theHunter.moveUp();
		this.setChanged();
		notifyObservers();
	}

	public void makeMoveDown() {
		theHunter.moveDown();
		this.setChanged();
		notifyObservers();
	}

	public void makeMoveLeft() {
		theHunter.moveLeft();
		this.setChanged();
		notifyObservers();
	}

	public void makeMoveRight() {
		theHunter.moveRight();
		this.setChanged();
		notifyObservers();
	}

	public void makeShootLeftOrRight() {
		theHunter.shootLeftOrRight();
		this.setChanged();
		notifyObservers();
	}

	public void makeShootUpOrDown() {
		theHunter.shootUpOrDown();
		this.setChanged();
		notifyObservers();
	}

	public Hunter getHunter() {
		return theHunter;
	}

	public Wumpus getWumpus(){
		return theWumpus;
	}
}
