package server;

import java.util.ArrayList;
import java.util.List;

public class Table {
	public int smBlind = 1;
	public int bBlind = 2;
	public int inChip = 200;
	public int seatNumber = 2;
	
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
		
	}
	
	public void addPlayer(Player player) {
		playerList.add(player);
	}
	
	public void startGame() {
		this.game = new Game(this);
		game.init();
		game.start();
	}
	
	public int getPlayerNumber() {
		return playerList.size();
	}
	
	public List getPlayerList() {
		return this.playerList;
	}
	
}
