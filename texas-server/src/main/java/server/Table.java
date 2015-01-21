package server;

import java.util.ArrayList;
import java.util.List;

public class Table {
	public static int ante = 0;
	public static int smBlind = 1;
	public static int bBlind = 2;
	public static int inChip = 200;
	public static int seatNumber = 2;
	
	public int smBlindIndex = 0;
	private static Table table = null;
	
	private List<Player> playerList;
	private Game game;
	
	private Table() {
		this.playerList = new ArrayList<Player>();
	}
	
	public static Table getTableInstance() {
		if(null != table) {
			return table;
		} else {
			return table = new Table();
		}
	}
	
	public void initTable() {
		game.initChip();
	}
	
	public void addPlayer(Player player) {
		playerList.add(player);
	}
	
	public void startGame() {
		this.game = new Game(this);
		game.init();
		initTable();
		game.start();
	}
	
	public int getPlayerNumber() {
		return playerList.size();
	}
	
	public List<Player> getPlayerList() {
		return this.playerList;
	}
	
}
