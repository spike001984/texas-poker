package server.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import server.Player;

public class TableView {

	public static int WIDTH = 1000;
	public static int HIGHT = 500;
	public static int MAX_PLAYER_NUM = 10;
	
	private JFrame frame;
	private JPanel centerPanel;
	private ArrayList<JTextArea> playerArea;
	private JTextArea logArea;
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
		
		playerArea = new ArrayList<JTextArea>();
		JTextArea tmp;
		
		for ( int i = 0; i < playerNum; i++ ) {
			tmp = new JTextArea();
			tmp.setEditable(false);
			tmp.setText("Player: " + i);
			playerArea.add(tmp);
			centerPanel.add(tmp);
		}
		
		logArea = new JTextArea();
		logArea.setEditable(false);
		logArea.setLineWrap(true);
		logArea.setColumns(20);
		
		JScrollPane scrollPane = new JScrollPane(logArea);
		
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		frame.getContentPane().add(scrollPane, BorderLayout.WEST);
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
		//text += player.getPrintMsg();
		playerArea.get(index).setText(text);
	}
	
	public void updateAllPlayer(ArrayList<Player> players) {
		if (players.size() != playerNum) {
			System.err.println("Update all player failed. Size unfit!");
			return;
		}
		
		for ( int i = 0; i < players.size(); i++ ) {
			updatePlayer(players.get(i), i);
		}
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
			playerArea.get(actingPlayer).setBorder(null);
		}
		
		if (index >= 0) {
			playerArea.get(index).setBorder(new EtchedBorder());
		}
		
		actingPlayer = index;
	}
	
	public void removePlayer(int index) {
		if (index < 0 || index >= playerNum || index == actingPlayer) {
			System.out.println("remove player failed. invalid index: " + index);
		}
		
		
	}
	
	public void appendLog(String log) {
		logArea.append(log + "\n");
		logArea.setCaretPosition(logArea.getText().length());
	}
	
	public void clear() {
		
	}
	
	public static void main(String[] args) {
		TableView view = new TableView(8);
		view.show();
		
		view.updatePlayer(new Player(200, null), 1);
		view.setActingPlayer(4);
		view.setActingPlayer(5);
		
		for ( int i = 0; i < 50; i++ ) {
			view.appendLog("Player 123 connected");
		}
		
	}
}
