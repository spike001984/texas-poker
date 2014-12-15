package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import util.FinalCard;

public class Player {
//	public static final String[] STATES = {"WAIT", "CHECK", "BET", "RAISE", "RERAISE", "FOLD", "ALL-IN", "CALL", "LOSE"};
	
	public static class States{
		public static String WAIT = "WAIT";	//0
		public static String CHECK = "CHECK";	//1
		public static String BET = "BET";	//2
		public static String RAISE = "RAISE";	//3
		public static String RERAISE = "RERAISE";	//4
		public static String FOLD = "FOLD";	//5
		public static String ALL_IN = "ALL-IN";	//6
		public static String CALL = "CALL";	//7
		public static String LOSE = "LOSE";	//8
	}
	
	private int chip;
	private String state;
	private Socket socket;
	private int actionChip;
	private int totalIn;

	private List<Card> cards;
	private String initMsg;
	
	/**
	 * For result calculating.
	 */
	private FinalCard finalCard;
	public int loseCounter = 0;  //The number of player whose final card is bigger than self.
	
	public FinalCard getFinalCard() {
		return finalCard;
	}

	public void setFinalCard(FinalCard finalCard) {
		this.finalCard = finalCard;
	}

	public Player(int chip, Socket socket) {
		this.chip = chip;
		this.socket = socket;
		this.state = States.WAIT;
		this.actionChip = 0;
		this.totalIn = 0;
		this.cards = new ArrayList<Card>(2);
	}
	
	public int getChip() {
		return chip;
	}
	
	public void setChip(int chip) {
		this.chip = chip;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	
	public int getActionChip() {
		return actionChip;
	}

	public void setActionChip(int actionChip) {
		this.actionChip = actionChip;
	}

	public int getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(int totalIn) {
		this.totalIn = totalIn;
	}

	public int check() {
		this.state = States.CHECK;
		return 0;
	}
		
	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public int bet(int n) {
		int result = putChip(n);
		if(0 == this.chip){
			this.setState(States.ALL_IN);
		}else {
			this.setState(States.BET);
		}
		return result;
	}	
	
	public int raise(int n) {
		int result = putChip(n);
		if (0 == this.chip) {
			this.setState(Player.States.ALL_IN);
		} else {
			this.setState(Player.States.RAISE);
		}
		return result;
	}
	
	public int reraise(int n) {
		int result = putChip(n);
		if (0 == this.chip) {
			this.setState(States.ALL_IN);
		} else {
			this.setState(States.RERAISE);
		}
		return result;
	}
	
	public int allIn() {
		int result = putChip(this.chip+this.actionChip);
		this.setState(States.ALL_IN);
		return result;
	}
	
	public int fold() {
		this.setState(States.FOLD);
		return 0;
	}
	
	public int call(int n) {
		int result = putChip(n);
		if (0 == this.chip) {
			this.setState(States.ALL_IN);
		} else {
			this.setState(States.CALL);
		}
		return result;
	}
	
	public int putChip(int n){
		chip += actionChip;
		totalIn -= actionChip;
		
		int number = 0;
		if(chip <= n){
			number = chip;
			chip = 0;
		}else {
			number = n;
			chip -= n;
		}
		actionChip = number;
		totalIn += actionChip;
		
		return number;
	}
	
	/**
	 * This message is sent to client in format:
	 * pid,total_chip,state,action_chip
	 * @return
	 */
	public String getUpdateMassage() {
		String msg = getPid() + "," + getChip() + "," + getState() + ","
				+ getActionChip();
		return msg;
	}

	public String getPrintMsg() {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		if (socket != null) {
			builder.append("ID: " + socket.getPort()+"\n");
		}
		builder.append("chip: " + this.chip+"\n");
		builder.append("actionChip: " + actionChip+"\n");
		builder.append("totalIn: " + this.totalIn + "\n");
		builder.append("finalCards: " + finalCard+"\n");
		
		builder.append("hand: " + this.cards.toString()+"\n");
		builder.append("state: " + this.state+"\n");
		return builder.toString();
	}
	
	public int getPid() {
		return getSocket().getPort();
	}

	public String getInitMsg() {
		return initMsg;
	}

	public void setInitMsg(String initMsg) {
		this.initMsg = initMsg;
	}

}
