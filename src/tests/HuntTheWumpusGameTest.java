/**
 * Programmed by BenlongHuang
 * Java Class Name: HuntTheWumpusGameTest.java
 * 
 * Behavior: This class is JUnit Test case of the HuntTheWumpusGame
 * 
 * 
 **/
package tests;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;
import model.HuntTheWumpusGame;

public class HuntTheWumpusGameTest {

	@Test
	public void test() {
		Random random = new Random();
		HuntTheWumpusGame theGame = new HuntTheWumpusGame(random);
		assertEquals(' ', theGame.getElement());
		theGame.makeMoveUp();
		theGame.makeMoveDown();
		theGame.getHunter().moveDown();
		theGame.makeMoveLeft();
		theGame.makeMoveRight();
		theGame.makeShootLeftOrRight();
		theGame.makeShootUpOrDown();
		theGame.getWumpus();
		assertFalse(theGame.getWinOrLose());
		assertTrue(theGame.isGameEnd());
		System.out.println(theGame.toString());
		System.out.println();
		System.out.println(theGame.toStringEnd());
	}
}