/**
 * Programmed by BenlongHuang
 * Java Class Name: TextView.java
 * 
 * Behavior: This class extends JPanel and implements Observer. It is one tab of the whole GUI. 
 * It contains the move buttons and shoot buttons and a large JTextArea and a ButtonListener implements ActionListener
 * 
 * 
 **/
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.HuntTheWumpusGame;

public class TextView extends JPanel implements Observer {
	private JTextArea gameArea;
	private HuntTheWumpusGame theGame;
	private JButton controlUp = new JButton("⬆︎up");
	private JButton controlDown = new JButton("⬇︎down");
	private JButton controlLeft = new JButton("⬅︎left");
	private JButton controlRight = new JButton("➜right");
	private JButton shootUp = new JButton("☝︎UP");
	private JButton shootDown = new JButton("☟DOWN");
	private JButton shootLeft = new JButton("☜LEFT");
	private JButton shootRight = new JButton("☞RIGHT");
	private JLabel gameInfo = new JLabel();
	private JLabel gameOver = new JLabel();

	public TextView(HuntTheWumpusGame HuntTheWumpusGame) {
		setFocusable(true);
		this.requestFocusInWindow();

		theGame = HuntTheWumpusGame;
		gameArea = new JTextArea();

		gameArea.setFont(new Font("Courier", Font.BOLD, 18));
		gameArea.setText(theGame.toString());
		gameArea.setEditable(false);
		JLabel instruction1 = new JLabel();
		instruction1.setFont(new Font("Times", Font.BOLD, 18));
		instruction1.setText("You can use our control buttons to control the hunter.");
		JLabel instruction2 = new JLabel();
		instruction2.setFont(new Font("Times", Font.BOLD, 18));
		instruction2.setText("Left four buttons are for move, right for shoot");

		JPanel theButtons = new JPanel();
		JPanel controlButton = new JPanel();
		JPanel shootButton = new JPanel();
		theButtons.add(controlButton);
		theButtons.add(shootButton);

		// buttons
		controlUp.addActionListener(new ButtonListener());
		controlDown.addActionListener(new ButtonListener());
		controlLeft.addActionListener(new ButtonListener());
		controlRight.addActionListener(new ButtonListener());
		shootUp.addActionListener(new ButtonListener());
		shootDown.addActionListener(new ButtonListener());
		shootLeft.addActionListener(new ButtonListener());
		shootRight.addActionListener(new ButtonListener());

		JPanel controlThree = new JPanel();
		JPanel controlOne = new JPanel();
		controlOne.add(controlUp);
		controlThree.add(controlLeft);
		controlThree.add(controlDown);
		controlThree.add(controlRight);

		controlButton.add(controlOne);
		controlButton.add(controlThree);

		JPanel shootOne = new JPanel();
		JPanel shootThree = new JPanel();
		shootOne.add(shootUp);
		shootThree.add(shootLeft);
		shootThree.add(shootDown);
		shootThree.add(shootRight);

		shootButton.add(shootOne);
		shootButton.add(shootThree);

		controlButton.setLayout(new GridLayout(2, 1));
		shootButton.setLayout(new GridLayout(2, 1));

		theButtons.setBorder(BorderFactory.createLineBorder(Color.black));

		// information about the game
		gameInfo.setText("Just started.");
		JPanel information = new JPanel();
		information.setPreferredSize(new Dimension(250, 80));
		information.add(gameInfo);
		information.add(gameOver);

		information.setLayout(new GridLayout(2, 1, 10, 10));

		gameOver.setText("Game continued...");

		this.add(instruction1);
		this.add(instruction2);
		this.add(theButtons);
		this.add(gameArea);
		this.add(information);
	}

	public void setWinOrLose() {
		if (theGame.getWinOrLose())
			gameInfo.setText("You Won!  You killed the Wumpus!");
		else
			gameInfo.setText("You Lost!  You Killed yourself!");
	}
	
	// end game set the buttons off
	public void endGame() {
		controlUp.setEnabled(false);
		controlDown.setEnabled(false);
		controlLeft.setEnabled(false);
		controlRight.setEnabled(false);

		shootUp.setEnabled(false);
		shootDown.setEnabled(false);
		shootLeft.setEnabled(false);
		shootRight.setEnabled(false);

		gameArea.setText(theGame.toStringEnd());
		gameOver.setText("GAME OVER!!!");
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (theGame.isGameEnd()) {
			gameArea.setText(((HuntTheWumpusGame) o).toStringEnd());
			if (theGame.getWinOrLose() && theGame.getHunter().getArrow())
				gameInfo.setText("You Won!  You killed the Wumpus!");
			if (!theGame.getWinOrLose() && theGame.getHunter().getArrow())
				gameInfo.setText("You Lost!  You Killed yourself!");
			gameOver.setText("GAME OVER!!!");
			endGame();

		} else
			gameArea.setText(((HuntTheWumpusGame) o).toString());
		repaint();
		checkStatus(null);

	}

	private void checkStatus(String direction) {
		if (direction != null)
			gameInfo.setText("You moved " + direction + ".");
		if (theGame.getElement() != ' ') {
			if (theGame.getElement() == 'S')
				gameInfo.setText("Slime!!!");
			if (theGame.getElement() == 'B')
				gameInfo.setText("Blood!!!");
			if (theGame.getElement() == 'G')
				gameInfo.setText("Goop!!!");
			if (theGame.getElement() == 'P') {
				gameInfo.setText("You walked into a bottomless pit!!!");
				theGame.endGame();
				endGame();
			}
			if (theGame.getElement() == 'W') {
				gameInfo.setText("You are EATEN by the Wumpus!!!");
				theGame.endGame();
				endGame();
			}
		}

	}

	/*
	 * The inner class of BUttonListener implements ActionListener
	 * */
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton theButton = (JButton) e.getSource();
			if (theButton.equals(controlUp)) {
				theGame.makeMoveUp();
				checkStatus("up");
				if (!theGame.isGameEnd())
					gameArea.setText(theGame.toString());
			}
			if (theButton.equals(controlDown)) {
				theGame.makeMoveDown();
				checkStatus("down");
				if (!theGame.isGameEnd())
					gameArea.setText(theGame.toString());
			}
			if (theButton.equals(controlLeft)) {
				theGame.makeMoveLeft();
				checkStatus("left");
				if (!theGame.isGameEnd())
					gameArea.setText(theGame.toString());
			}
			if (theButton.equals(controlRight)) {
				theGame.makeMoveRight();
				checkStatus("right");
				if (!theGame.isGameEnd())
					gameArea.setText(theGame.toString());
			}

			if (theButton.equals(shootUp) || theButton.equals(shootDown)) {
				theGame.makeShootUpOrDown();
				endGame();
				setWinOrLose();
			}

			if (theButton.equals(shootLeft) || theButton.equals(shootRight)) {
				theGame.makeShootLeftOrRight();
				endGame();
				setWinOrLose();
			}

		}
	}

}
