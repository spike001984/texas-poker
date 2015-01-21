package client.lib;

import java.util.ArrayList;
import java.util.List;

public class PlayerList {
	private List<Player> players;
	
	private int selfIndex;

	public PlayerList() {
		this.players = new ArrayList<Player>();
	}
	
	public void initPlayerList(int allchip, String msg, String self) {
		String[] playerIds = msg.split(",");
		for(int i = 0; i < playerIds.length; i++){
			if(playerIds[i].equals(self)){
				this.selfIndex = i;
			}
			Player player = new Player(Integer.parseInt(playerIds[i]), allchip);
			players.add(player);
			
		}
	}

	public void update(List<String> playerStates) {
		// TODO Auto-generated method stub
		for(int i = 0; i < playerStates.size(); i++){
			String[] message = playerStates.get(i).split(",");
			int chip = Integer.parseInt(message[1]);
			String state = message[2];
			int actionNumber = Integer.parseInt(message[3]);
		}
	}
	
}
