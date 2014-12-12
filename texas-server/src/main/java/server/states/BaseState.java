package server.states;

import java.util.List;

import server.Game;
import server.Player;
import server.decoder.MassageDecoder;
import server.sender.MassageSender;

public abstract class BaseState {
	protected Game game;
	protected String state;
	
	private int betChip;
	
	public BaseState(Game game) {
		this.game = game;
		this.betChip = 0;
	}
	
	public void action(){
		beforePlayerAction();
		int smIndex = game.getTable().smBlindIndex;
		playerAction((smIndex+2) % game.getPlayerList().size());
	}
	public abstract void beforePlayerAction();
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return state;
	}
	
	public int getBetChip() {
		return betChip;
	}

	public void setBetChip(int betChip) {
		this.betChip = betChip;
	}

	public void playerAction(int startIndex) {
		List<Player> playerList = game.getPlayerList();
		for(int i = startIndex; !game.isOnlyOneAlive() && !game.isAllCall(); i = (i+1)%playerList.size()) {
			Player player = playerList.get(i);
//			MassageSender.updateView(playerList, game.getUpdateMassage(player));
			MassageSender.updateView(player, game.getUpdateMassage(player));
			String availableAction = game.getPlayerAvalableAction(i);
			if(null == availableAction) {
				continue;
			}
			game.printGameState();
			MassageSender.yourTurn(player, availableAction);
			MassageDecoder.process(game, player);
			game.printGameState();
		}
		next();
	}
	
	public abstract void next();
}
