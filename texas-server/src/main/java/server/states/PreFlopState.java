package server.states;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import server.Game;
import server.Player;
import server.sender.MassageSender;
import server.utils.IOHelper;

public class PreFlopState extends BaseState{
	
	private int betChip;

	public PreFlopState(Game game) {
		super(game);
		this.state = "preflop";
	}	
	
	public int getBetChip() {
		return betChip;
	}

	public void setBetChip(int betChip) {
		this.betChip = betChip;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		if(game.isOnlyOneAlive()){
			game.setState(new GameEndingState(game));
		} else if (game.isAllCall()) {
			game.setState(new FlopState(game));
		}
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Player> playerList = this.game.getPlayerList();
		for(int i = 0; i < playerList.size(); i++){
			Player player = (Player) playerList.get(i);
			PrintWriter pw = null;
			try {
				pw = IOHelper.getWriter(player.getSocket());
				MassageSender.init(pw, player.getChip());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {

			}
		}
		MassageSender.updateView(playerList, game.getUpdateMassage());
		
		next();
	}

}
