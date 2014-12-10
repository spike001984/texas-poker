package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Player {
	public static final String[] STATES = {"WAIT", "CHECK", "BET", "RAISE", "RERAISE", "FOLD", "ALL-IN", "CALL"};
	private int chip;
	private String state;
	private Socket socket;
	private int actionChip;
	private int totalIn;
	
	private List<Card> cards;
	
	public Player(int chip, Socket socket) {
		this.chip = chip;
		this.socket = socket;
		this.state = Player.STATES[0];
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
		this.state = Player.STATES[1];
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
			this.setState(Player.STATES[6]);
		}else {
			this.setState(Player.STATES[2]);
		}
		return result;
	}	
	
	public int raise(int n) {
		int result = putChip(n);
		if (0 == this.chip) {
			this.setState(Player.STATES[6]);
		} else {
			this.setState(Player.STATES[3]);
		}
		return result;
	}
	
	public int reraise(int n) {
		int result = putChip(n);
		if (0 == this.chip) {
			this.setState(Player.STATES[6]);
		} else {
			this.setState(Player.STATES[4]);
		}
		return result;
	}
	
	public int allIn() {
		int result = putChip(this.chip+this.actionChip);
		this.setState(Player.STATES[6]);
		return result;
	}
	
	public int fold() {
		this.setState(Player.STATES[5]);
		return 0;
	}
	
	public int call(int n) {
		int result = putChip(n);
		if (0 == this.chip) {
			this.setState(Player.STATES[6]);
		} else {
			this.setState(Player.STATES[7]);
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
	
	public String getUpdateMassage() {
		StringBuilder builder = new StringBuilder();
		builder.append(socket.getPort()+",");
		builder.append(this.chip+",");
		builder.append(this.state+",");
		builder.append(this.actionChip);
		return builder.toString();
	}
}
