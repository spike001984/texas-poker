package server.states;

import server.Game;
import server.Player;

public class FlopState extends BaseState{

	public FlopState(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		this.state = "flop";
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

	@Override
	public void beforePlayerAction() {
		// TODO Auto-generated method stub
		game.initPlayersState();
		deal2board();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		if(game.isOnlyOneAlive()){
			game.setState(new GameEndingState(game));
		} else if (game.isAllCall()) {
			game.setState(new TurnState(game));
		}
	}
}
