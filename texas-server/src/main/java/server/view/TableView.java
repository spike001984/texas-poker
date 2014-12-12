package server.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import server.Player;

public class TableView {

	public static int WIDTH = 800;
	public static int HIGHT = 500;
	public static int MAX_PLAYER_NUM = 10;
	
	private JFrame frame;
	private JPanel centerPanel;
	private JTextArea[] playerArea;
	private int playerNum;
	private int actingPlayer = -1;
	
	public TableView(int playerNum) {
		if (playerNum > MAX_PLAYER_NUM) {
			System.err.println("Tabie View's player num bigger than 10!");
			return;
		}
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLocationRelativeTo(null);
		frame.setTitle("Texas Poker Server");
		frame.setSize(WIDTH, HIGHT);
		frame.setResizable(false);
		frame.setLocation(200, 100);
		
		this.playerNum = playerNum;
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 5));
		
		playerArea = new JTextArea[playerNum];
		
		for ( int i = 0; i < playerNum; i++ ) {
			playerArea[i] = new JTextArea();
			playerArea[i].setEditable(false);
			centerPanel.add(playerArea[i]);
			playerArea[i].setText("Player: " + i);
		}
		
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * use to show GUI.
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	/**
	 * Call when player status is changed.
	 * @param player
	 * @param index
	 */
	public void updatePlayer(Player player, int index) {
		if (index >= playerNum) {
			System.err.println("update player view failed! invalid index!");
			return;
		}
		
		String text = "Player " + index + ":\n";
		text += player.getPrintMsg();
		playerArea[index].setText(text);
	}
	
	/**
	 * Set acting player index.
	 * @param index -1 if reset.
	 */
	public void setActingPlayer(int index) {
		if (index >= playerNum) {
			System.err.println("Set acting player failed! Index out of bounds!");
			return;
		}
		
		//Rest acting player.
		if (actingPlayer >= 0) {
			playerArea[actingPlayer].setBorder(null);
		}
		
		if (index >= 0) {
			playerArea[index].setBorder(new EtchedBorder());
		}
		
		actingPlayer = index;
	}
	
	public void clear() {
		
	}
	
	public static void main(String[] args) {
		TableView view = new TableView(8);
		view.show();
		
		view.updatePlayer(new Player(200, null), 1);
		view.setActingPlayer(4);
		view.setActingPlayer(5);
	}
}
