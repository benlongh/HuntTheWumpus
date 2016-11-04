/**
 * Programmed by BenlongHuang
 * Java Class Name: ImageView.java
 * Behavior: This class extends JPanel and implements Observer. It is one tab of the whole GUI. 
 * It contains the move buttons and shoot buttons and a large JPanel inside it and a inner class which draws the JPanel in the view
 * 
 * 
 **/
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.HuntTheWumpusGame;

public class ImageView extends JPanel implements Observer {

	private HuntTheWumpusGame theGame;
	private JButton controlUp = new JButton("⬆︎up");
	private JButton controlDown = new JButton("⬇︎down");
	private JButton controlLeft = new JButton("⬅︎left");
	private JButton controlRight = new JButton("➜right");
	private JButton shootUp = new JButton("☝︎UP");
	private JButton shootDown = new JButton("☟DOWN"); 
	private JButton shootLeft = new JButton("☜LEFT");
	private JButton shootRight = new JButton("☞RIGHT");
	private JLabel gameOver = new JLabel();
	private GameArea gameArea = new GameArea();

	public ImageView(HuntTheWumpusGame game) {
		setFocusable(true);
		this.requestFocusInWindow();
		theGame = game;
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

		// control and shoot buttons
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

		gameArea.setPreferredSize(new Dimension(500, 500));
		gameArea.setBackground(Color.BLACK);

		gameOver.setText("Game continued...");

		this.add(instruction1);
		this.add(instruction2);
		this.add(theButtons);
		this.add(gameArea);
		this.add(gameOver);

	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
		checkStatus();
		if (theGame.isGameEnd()) {
			if (theGame.getWinOrLose() && theGame.getHunter().getArrow())
				gameOver.setText("You Won!  You killed the Wumpus!");
			if (!theGame.getWinOrLose() && theGame.getHunter().getArrow())
				gameOver.setText("You Lost!  You Killed yourself!");
			endGame();
		}
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
	}

	private class GameArea extends JPanel {
		private Image blood;
		private Image goop;
		private Image ground;
		private Image slime;
		private Image pit;
		private Image hunter;
		private Image wumpus;

		public GameArea() {
			try {
				blood = ImageIO.read(new File("WumpusImageSet/Blood.png"));

				goop = ImageIO.read(new File("WumpusImageSet/Goop.png"));
				ground = ImageIO.read(new File("WumpusImageSet/Ground.png"));
				slime = ImageIO.read(new File("WumpusImageSet/Slime.png"));
				pit = ImageIO.read(new File("WumpusImageSet/SlimePit.png"));
				hunter = ImageIO.read(new File("WumpusImageSet/TheHunter.png"));
				wumpus = ImageIO.read(new File("WumpusImageSet/Wumpus.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g);

			for (int i = 0; i < theGame.getSize(); i++) {
				for (int j = 0; j < theGame.getSize(); j++) {
					if ((theGame.isGameEnd() ? theGame.actualBoard : theGame.userBoard)[i][j] == ' ')
						g2.drawImage(ground, 50 * j, 50 * i, this);
					if ((theGame.isGameEnd() ? theGame.actualBoard : theGame.userBoard)[i][j] == 'O') {
						g2.drawImage(ground, 50 * j, 50 * i, this);
						if ((theGame.isGameEnd() ? theGame.actualBoard : theGame.userBoard)[i][j] == 'O') {
							g2.drawImage(ground, 50 * j, 50 * i, this);
							if (theGame.getHunter().getWhatsInTheRoom() == 'S')
								g2.drawImage(slime, 50 * j, 50 * i, this);
							if (theGame.getHunter().getWhatsInTheRoom() == 'B')
								g2.drawImage(blood, 50 * j, 50 * i, this);
							if (theGame.getHunter().getWhatsInTheRoom() == 'G')
								g2.drawImage(goop, 50 * j, 50 * i, this);
							if (theGame.getHunter().getWhatsInTheRoom() == 'P')
								g2.drawImage(pit, 50 * j, 50 * i, this);
							if (theGame.getHunter().getWhatsInTheRoom() == 'W')
								g2.drawImage(wumpus, 50 * j, 50 * i, this);
							g2.drawImage(hunter, 50 * j, 50 * i, this);
						}
						g2.drawImage(hunter, 50 * j, 50 * i, this);
					}
					if ((theGame.isGameEnd() ? theGame.actualBoard : theGame.userBoard)[i][j] == 'S') {
						g2.drawImage(ground, 50 * j, 50 * i, this);
						g2.drawImage(slime, 50 * j, 50 * i, this);
						if (theGame.getHunter().X == i && theGame.getHunter().Y == j)
							g2.drawImage(hunter, 50 * j, 50 * i, this);
					}
					if ((theGame.isGameEnd() ? theGame.actualBoard : theGame.userBoard)[i][j] == 'G') {
						g2.drawImage(ground, 50 * j, 50 * i, this);
						g2.drawImage(goop, 50 * j, 50 * i, this);
						if (theGame.getHunter().X == i && theGame.getHunter().Y == j)
							g2.drawImage(hunter, 50 * j, 50 * i, this);
					}
					if ((theGame.isGameEnd() ? theGame.actualBoard : theGame.userBoard)[i][j] == 'P') {
						g2.drawImage(ground, 50 * j, 50 * i, this);
						g2.drawImage(pit, 50 * j, 50 * i, this);
						if (theGame.getHunter().X == i && theGame.getHunter().Y == j)
							g2.drawImage(hunter, 50 * j, 50 * i, this);
					}
					if ((theGame.isGameEnd() ? theGame.actualBoard : theGame.userBoard)[i][j] == 'W') {
						g2.drawImage(ground, 50 * j, 50 * i, this);
						g2.drawImage(wumpus, 50 * j, 50 * i, this);
						if (theGame.getHunter().X == i && theGame.getHunter().Y == j)
							g2.drawImage(hunter, 50 * j, 50 * i, this);
					}
					if ((theGame.isGameEnd() ? theGame.actualBoard : theGame.userBoard)[i][j] == 'B') {
						g2.drawImage(ground, 50 * j, 50 * i, this);
						g2.drawImage(blood, 50 * j, 50 * i, this);
						if (theGame.getHunter().X == i && theGame.getHunter().Y == j)
							g2.drawImage(hunter, 50 * j, 50 * i, this);
					}
				}
			}

		}

	}

	public void setWinOrLose() {
		if (theGame.getWinOrLose())
			gameOver.setText("You Won!  You killed the Wumpus! GAME OVER!!");
		else
			gameOver.setText("You Lost!  You Killed yourself! GAME OVER!!");
	}

	// set all buttons off
	public void endGame() {
		controlUp.setEnabled(false);
		controlDown.setEnabled(false);
		controlLeft.setEnabled(false);
		controlRight.setEnabled(false);

		shootUp.setEnabled(false);
		shootDown.setEnabled(false);
		shootLeft.setEnabled(false);
		shootRight.setEnabled(false);
	}

	private void checkStatus() {
		if (theGame.getElement() != ' ') {
			if (theGame.getElement() == 'P') {
				gameOver.setText("You walked into a bottomless pit!!! GAME OVER!!!");
				theGame.endGame();
				endGame();
			}
			if (theGame.getElement() == 'W') {
				gameOver.setText("You are EATEN by the Wumpus!!! GAME OVER!!!");
				theGame.endGame();
				endGame();
			}
		}

	}

	/*
	 * This is the inner class, ButtonListeneer implements ActionListener
	 * */
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton theButton = (JButton) e.getSource();
			if (theButton.equals(controlUp)) {
				theGame.makeMoveUp();
			}
			if (theButton.equals(controlDown)) {
				theGame.makeMoveDown();
			}
			if (theButton.equals(controlLeft)) {
				theGame.makeMoveLeft();
			}
			if (theButton.equals(controlRight)) {
				theGame.makeMoveRight();
			}

			// every time we shoot, we make the game end
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
			checkStatus();
		}
	}

}
