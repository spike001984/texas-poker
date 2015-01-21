package server.states;

import java.util.List;

import server.Game;
import server.Player;
import server.Table;
import server.sender.MassageSender;

public class PreFlopState extends BaseState{

	public PreFlopState(Game game) {
		super(game);
		this.state = "preflop";
	}

	@Override
	public void beforePlayerAction() {
		// TODO Auto-generated method stub
		game.initPlayersState();
		setBlinds();
		dealCards();
		game.initBlinds();
	}
	
	private void setBlinds(){
		game.increaseSmBlindIndex();
		int smIndex = game.getTable().smBlindIndex;
		List<Player> playerList = game.getPlayerList();
		if(null != game.getPlayerAvalableAction(smIndex)){
			game.bet(playerList.get(smIndex), game.getTable().smBlind);
		}
		if(null != game.getPlayerAvalableAction((smIndex + 1) % playerList.size())){
			game.raise(playerList.get((smIndex + 1) % playerList.size()), game.getTable().bBlind);
		}
	}
	
	public void dealCards(){
		int smIndex = game.getTable().smBlindIndex;
		List<Player> playerList = game.getPlayerList();
		for(int i = 0; i < playerList.size(); i++){
			deal2player(playerList.get((i + smIndex) % playerList.size()));
		}
	}

	public void deal2player(Player player) {
		this.game.deal2player(player);
		this.game.deal2player(player);
		MassageSender.deal(player, game.getTable().smBlindIndex);
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
	
}
