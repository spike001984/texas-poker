package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.states.BaseState;
import server.states.FlopState;
import server.states.PreFlopState;

public class Game { 
	private int pot;
	private Table table;
	
	private Map chipMap;
	private BaseState state;
	private List<Player> playerList;
	
	private Deck deck;
	private List<Card> boardCards;
	
	
	private boolean isEnd;
	
	@SuppressWarnings("unchecked")
	public Game(Table table) {
		this.pot = 0;
		this.table = table;
		this.chipMap = new HashMap();
		this.state = (BaseState)new PreFlopState(this);
		this.playerList = table.getPlayerList();
		this.deck = Deck.getDeckInstance();
		this.boardCards = new ArrayList<Card>(7);
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
		this.setState(new FlopState(this));
		while(!isEnd){
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
	
	public List<Player> getPlayerList() {
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
		//add boardcards
		if(0 != boardCards.size()){
			builder.append(" " + boardCards.get(0));
			for(int i = 1; i < boardCards.size(); i++) {
				builder.append("," + boardCards.get(i));
			}
		}
		//add player info
		for(Player player : playerList) {
			builder.append(" " + player.getUpdateMassage());
		}
		return builder.toString();
	}
	
	public void processResult() {
		
	}
	
	public boolean isThereAWiner() {
		return false;
	}
	
	public void endTable(){
		
	}
	
	public Card deal2player(Player player) {
		Card card = deck.deal();
		player.getCards().add(card);
		return card;
	}
	
	public Card deal2Board() {
		Card card = deck.deal();
		boardCards.add(card);
		return card;
	}
}
