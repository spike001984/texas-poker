package server.states;

import server.Game;

public abstract class BaseState {
	protected Game game;
	protected String state;
	public BaseState(Game game) {
		this.game = game;
	}
	public abstract void action();
	public abstract void next();
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return state;
	}	
}
