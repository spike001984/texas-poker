package client.lib;

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
//		serverMessage = communicator.read();
		
		if(serverMessage.startsWith("init")){
			
		}
		if(serverMessage.startsWith("deal")){
			
		}
		if(serverMessage.startsWith("uv")){
			
		}
		if(serverMessage.startsWith("yt")){
			acitonProcess();
		}
	}
	
	/**
	 * action
	 */
	private void acitonProcess() {
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
	
	/*
	 * action methods call by user
	 */
	public void call() {
		
	}
	
	public void fold() {
		
	}
	
	public void check() {
		
	}
	
	public void raise(int chip) {
		
	}
	
	public void allIn() {
		
	}
	
	public void bet(int chip) {
		
	}
	
	/*
	 * methods to complet by user.
	 */
	public abstract void OnPreflop();
	public abstract void OnFlop();
	public abstract void OnTure();
	public abstract void OnRiver();
}
