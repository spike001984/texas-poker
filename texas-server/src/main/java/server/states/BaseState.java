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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void playerAction(int startIndex) {
		List<Player> playerList = game.getPlayerList();
		
		game.tableView.updateAllPlayer(playerList);
		game.tableView.appendLog(game.getBoardCards().toString());
		
		for(int i = startIndex; !game.isOnlyOneAlive() && !game.isAllCall(); i = (i+1)%playerList.size()) {
			Player player = playerList.get(i);
//			MassageSender.updateView(playerList, game.getUpdateMassage(player));
			MassageSender.updateView(player, game.getUpdateMassage(player));

			game.tableView.setActingPlayer(playerList.indexOf(player));
			game.tableView.updatePlayer(player, playerList.indexOf(player));
			
			String availableAction = game.getPlayerAvalableAction(i);
			if(null == availableAction) {
				continue;
			}
//			game.printGameState();
			
			MassageSender.yourTurn(player, availableAction);
			MassageDecoder.process(game, player);
			
			game.tableView.updatePlayer(player, playerList.indexOf(player));
			System.out.println(availableAction);
			System.out.println(player.getPrintMsg());
//			game.printGameState();
			
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		next();
	}
	
	public abstract void next();
}
