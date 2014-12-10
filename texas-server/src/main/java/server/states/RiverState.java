package server.states;

import server.Game;

public class RiverState extends BaseState{

	public RiverState(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		this.state = "river";
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
		game.setState(new GameEndingState(game));
	}
}
