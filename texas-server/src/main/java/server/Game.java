package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.states.BaseState;
import server.states.FlopState;
import server.states.PreFlopState;

public class Game {
	public static String[] PLAYER_ACTIONS = {"check", "bet", "call", "raise", "all-in", "fold"};
	
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
	
	public void initPlayersState(){
		for(Player player : playerList) {
			if(!player.getState().equals(Player.STATES[5])
					&& !player.getState().equals(Player.STATES[6])){
				player.setState(Player.STATES[0]);
				player.setActionChip(0);
			}
		}
	}
	
	public void initBlinds(){
		for(Player player : playerList) {
			if(player.getState().equals(Player.STATES[3])){
				player.setState(Player.STATES[0]);
			}
		}
	}
	
	
	public void increaseSmBlindIndex(){
		table.smBlindIndex = (table.smBlindIndex + 1) % playerList.size();
	}
	
	public void start() {
		while(!isEnd){
			state.action();
		}
	}
	
	public boolean isOnlyOneAlive() {
		int playerAlive = 0;
		for(Player player : playerList) {
			if(!player.getState().equals(Player.STATES[5])){
				playerAlive++;
				if(playerAlive > 1){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isAllCall() {
		
		for(Player player : playerList) {
			if((player.getState().equals(Player.STATES[0]))
					|| (player.getActionChip() != state.getBetChip() && !player.getState().equals(Player.STATES[5]) && !player.getState().equals(Player.STATES[6]))){
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
	
	public String getUpdateMassage(Player player) {
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
		int index = playerList.indexOf(player);
		for(int i = 1; i <= playerList.size(); i++){
			builder.append(" " + playerList.get((index + i) % playerList.size()).getUpdateMassage());
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
	
	/*
	 * return a part of "your turn" message
	 */
	public String getPlayerAvalableAction(int playerIndex) {
		Player currentPlayer = playerList.get(playerIndex);
		
		//player's state is fold or all-in
		if(currentPlayer.getState().equals(Player.STATES[6])
				||currentPlayer.getState().equals(Player.STATES[7])){
			return null;
		}
		//player's state is wait
		if(currentPlayer.getState().equals(Player.STATES[0])){
			if(currentPlayer.getActionChip() == state.getBetChip()) {
				return "0 1 1 0 1 1";
			}/*has enough chip*/else if(currentPlayer.getChip() >= state.getBetChip()-currentPlayer.getActionChip()){
				return "1 1 0 1 1 0";
			}/*not has enough chip*/else {
				return "0 1 0 0 1 0";
			}
		}
		
		if(currentPlayer.getChip() >= state.getBetChip()-currentPlayer.getActionChip()){
			return "1 1 0 1 1 0";
		} else {
			return "0 1 0 0 1 0";
		}
	}
	
	/*
	 *	player's action 
	 */
	public void bet(Player player, int chip){
		// TODO Auto-generated method stub
		pot = pot - player.getActionChip();
		int realChip = player.bet(chip);
		pot = pot + realChip;
		if(realChip > state.getBetChip()){
			state.setBetChip(realChip);
		}
	}
	
	public void raise(Player player, int chip){
		// TODO Auto-generated method stub
		pot = pot - player.getActionChip();
		int realChip = player.raise(chip);
		pot = pot + realChip;
		if(realChip > state.getBetChip()){
			state.setBetChip(realChip);
		}
	}
	
	public void call(Player player){
		// TODO Auto-generated method stub
		pot = pot - player.getActionChip();
		int realChip = player.call(state.getBetChip());
		pot = pot + realChip;
		if(realChip > state.getBetChip()){
			state.setBetChip(realChip);
		}
	}
	
	public void check(Player player){
		// TODO Auto-generated method stub
		player.check();
	}
	
	public void allIn(Player player){
		// TODO Auto-generated method stub
		pot = pot - player.getActionChip();
		int realChip = player.allIn();
		pot = pot + realChip;
		if(realChip > state.getBetChip()){
			state.setBetChip(realChip);
		}
	}
	
	public void fold(Player player){
		// TODO Auto-generated method stub
		player.fold();
	}
	
	public void printGameState() {
		System.out.println(state);
		System.out.println("Pot: " + pot);
		System.out.println("BetChip: " + state.getBetChip());
	}
}
