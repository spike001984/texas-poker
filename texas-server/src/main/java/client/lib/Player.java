package client.lib;

public class Player {
	private int chip;
	private int actionChip;
	private String state;
	
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
