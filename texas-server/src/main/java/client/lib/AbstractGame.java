package client.lib;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGame {
	private String state;
	
	private static class GameState{
		public static String PRE_FLOP = "PRE_FLOP";
		public static String FLOP = "FLOP";
		public static String TURN = "TURN";
		public static String RIVER = "RIVER";
	}
	
	private Communicator communicator;
	private CommandBuilder commandBuilder;
	private PlayerList playerList;
	
	private boolean isEnd;
	private String serverMessage;
	
	private int smblind, bigblind, ante, sbIndex, pot;
	
	private List<Card> playerCards;
	private List<Card> bordCards;
	
	public AbstractGame() {
		this.communicator = Communicator.getInstance();
		this.commandBuilder = new CommandBuilder();
		this.playerList = new PlayerList();
	}
	
	/**
	 * start game
	 */
	public void startGame(){
		while(!isEnd) {
			gameFloop();
		}
	}
	
	/**
	 * floop of the game
	 */
	private void gameFloop(){
		serverMessage = communicator.recive();;
		
		if(serverMessage.startsWith("init")){
			onReceiveInit(serverMessage);
		}
		if(serverMessage.startsWith("deal")){
			onReceiveDeal(serverMessage);
		}
		if(serverMessage.startsWith("uv")){
			onReceiveUv(serverMessage);
		}
		if(serverMessage.startsWith("yt")){
			OnReceiveYt(serverMessage);
		}
	}
	
	/**
	 * action
	 */
	private void OnReceiveYt(String msg) {
		if(state == GameState.PRE_FLOP){
			OnPreflop();
		}
		
		if(state == GameState.FLOP){
			OnFlop();
		}
		
		if(state == GameState.TURN){
			OnTure();
		}
		
		if(state == GameState.RIVER){
			OnRiver();
		}
	}
	
	public void onReceiveInit(String msg){
		this.playerCards.clear();
		this.bordCards.clear();
		
		String[] message = msg.split(" ");
		int allchip = Integer.parseInt(message[1]);
		this.smblind = Integer.parseInt(message[2]);
		this.bigblind = Integer.parseInt(message[3]);
		this.ante = Integer.parseInt(message[4]);
		
		playerList.initPlayerList(allchip, message[5], message[6]);
	}
	
	public void onReceiveDeal(String msg){
		String[] message = msg.split(" ");
//		this.playerCards.add(new Card(message[2]));
//		this.playerCards.add(new Card(message[3]));
		this.sbIndex = Integer.parseInt(message[4]);

	}
	
	public void onReceiveUv(String msg){
		String[] message = msg.split(" ");
		this.pot = Integer.parseInt(message[2]);
		this.state = message[3];
		String boardCards = message[4];
		List<String> playerStates = new ArrayList();
		for(int i = 5; i < message.length; i++) {
			playerStates.add(message[i]);
		}
		
		playerList.update(playerStates);
	}
	
	
	/*
	 * action methods call by user
	 */
	public void call() {
		communicator.send(this.commandBuilder.getCallCommand());
	}
	
	public void fold() {
		communicator.send(this.commandBuilder.getFoldCommand());
	}
	
	public void check() {
		communicator.send(this.commandBuilder.getFoldCommand());
	}
	
	public void raise(int chip) {
		communicator.send(this.commandBuilder.getRaiseCommand(chip));
	}
	
	public void allIn() {
		communicator.send(this.commandBuilder.getFoldCommand());
	}
	
	public void bet(int chip) {
		communicator.send(this.commandBuilder.getBetCommand(chip));
	}
	
	/*
	 * methods to complet by user.
	 */
	public abstract void OnPreflop();
	public abstract void OnFlop();
	public abstract void OnTure();
	public abstract void OnRiver();
}
