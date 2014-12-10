package server.states;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import server.Game;
import server.Player;
import server.sender.MassageSender;
import server.utils.IOHelper;

public class FlopState extends BaseState{

	public FlopState(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		this.state = "flop";
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		List<Player> playerList = this.game.getPlayerList();
		//deal to player
		dealPoket(playerList);
		//deal board cards
		deal2board();
		//update view
		MassageSender.updateView(playerList, this.game.getUpdateMassage());
		//player's action
		playerAction();
		
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}
	
	private void dealPoket(List<Player> playerList){
		int smBlindIndex = this.game.getSmBlind();
		
		for(int i = 0; i < playerList.size(); i++){
			Player player = (Player) playerList.get((i+smBlindIndex)%playerList.size());
			
			deal2player(player);
			
			PrintWriter pw = null;
			try {
				pw = IOHelper.getWriter(player.getSocket());
				MassageSender.deal(pw, player);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {

			}
		}

	}
	
	public void deal2player(Player player) {
		this.game.deal2player(player);
		this.game.deal2player(player);
	}

	public void deal2board(){
		this.game.deal2Board();
		this.game.deal2Board();
		this.game.deal2Board();
	}
	
	public void playerAction() {
		
	}
}
