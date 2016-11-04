/**
 * Programmed by BenlongHuang  
 * Java Class Name: HuntTheWumpusGameGUI.java
 * 
 * Behavior: This class is the Graphic User Interface of Hunt the Wumpus game for this project
 * it contains a JTabbedPane which has two tabs. Each of the tabs have a JPanel which is the two different views
 * 
 * 
 **/
package controller;

import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import model.HuntTheWumpusGame;
import view.ImageView;
import view.TextView;

public class HuntTheWumpusGUI extends JFrame {
	private HuntTheWumpusGame theGame;
	private TextView text;
	private ImageView image;

	public static void main(String[] args) {
		HuntTheWumpusGUI g = new HuntTheWumpusGUI();
		g.setVisible(true); 
	}

	public HuntTheWumpusGUI() {
		Random random = new Random();
		theGame = new HuntTheWumpusGame(random);
		text = new TextView(theGame);
		image = new ImageView(theGame);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 770);
		this.setTitle("Hunt The Wumpus");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		JTabbedPane tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("image", image);
		tabbedPane.addTab("text", text);
		this.add(tabbedPane);
		theGame.addObserver(text);
		theGame.addObserver(image);
	}

}
 