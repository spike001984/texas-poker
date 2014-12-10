package server.states;

import server.Game;

public class TurnState extends BaseState{

	public TurnState(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		this.state = "turn";
	}

	@Override
	public void beforePlayerAction() {
		// TODO Auto-generated method stub
		game.initPlayersState();
		deal2board();
	}
	
	public void deal2board(){
		this.game.deal2Board();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		if(game.isOnlyOneAlive()){
			game.setState(new GameEndingState(game));
		} else if (game.isAllCall()) {
			game.setState(new RiverState(game));
		}
	}
}
