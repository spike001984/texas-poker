package server.states;

import server.Game;

public class GameEndingState extends BaseState{

	public GameEndingState(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		this.game.processResult();
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		if (this.game.isThereAWiner()){
			this.game.endTable();
		} else {
			this.game.init();
			this.game.setState(new PreFlopState(this.game));
		}
	}

	@Override
	public void beforePlayerAction() {
		// TODO Auto-generated method stub
		
	}

}
