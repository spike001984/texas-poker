package server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.states.BaseState;
import server.states.PreFlopState;

public class Game { 
	private int pot;
	private Table table;
	
	private Map chipMap;
	private BaseState state;
	private List<Player> playerList;
	
	private boolean isEnd;
	
	@SuppressWarnings("unchecked")
	public Game(Table table) {
		this.pot = 0;
		this.table = table;
		this.chipMap = new HashMap();
		this.state = (BaseState)new PreFlopState(this);
		this.playerList = table.getPlayerList();
		this.isEnd = false;
	}
	
	
	
	public int getPot() {
		return pot;
	}



	public void setPot(int pot) {
		this.pot = pot;
	}



	public Table getTable() {
		return table;
	}



	public void setTable(Table table) {
		this.table = table;
	}



	public Map getChipMap() {
		return chipMap;
	}



	public void setChipMap(Map chipMap) {
		this.chipMap = chipMap;
	}



	public BaseState getState() {
		return state;
	}



	public void setState(BaseState state) {
		this.state = state;
	}



	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}



	public void init() {
		
	}
	
	public void start() {
		while(isEnd){
			state.action();
		}
	}
	
	public boolean isOnlyOneAlive() {
		int playerAlive = 0;
		for(Player player : playerList) {
			if(!player.getState().equals(Player.STATES[5])){
				playerAlive++;
				if(playerAlive>1){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isAllCall() {
		int inChip = playerList.get(0).getActionChip();
		for(Player player : playerList) {
			if(inChip != player.getActionChip()){
				return false;
			}
		}
		return true;
	}
	
	public List getPlayerList() {
		return this.playerList;
	}
	
	public int getSmBlind() {
		return table.smBlindIndex;
	}
	
	public String getUpdateMassage() {
		StringBuilder builder = new StringBuilder();
		builder.append("uv ");
		builder.append(table.getPlayerNumber() + " ");
		builder.append(pot+ " ");
		builder.append(state);
		for(Player player : playerList) {
			builder.append(" " + player.getUpdateMassage());
		}
		return builder.toString();
	}
}
