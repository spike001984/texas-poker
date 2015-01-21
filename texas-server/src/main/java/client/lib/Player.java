package client.lib;

public class Player {
	private int id;
	private int chip;
	private int actionChip;
	private String state;
	
	public Player(int id, int chip) {
		this.id = id;
		this.chip = chip;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChip() {
		return chip;
	}
	public void setChip(int chip) {
		this.chip = chip;
	}
	public int getActionChip() {
		return actionChip;
	}
	public void setActionChip(int actionChip) {
		this.actionChip = actionChip;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
