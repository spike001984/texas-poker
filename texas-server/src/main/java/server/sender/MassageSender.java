package server.sender;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import server.Card;
import server.Player;
import server.utils.IOHelper;

public class MassageSender {

	/**
	 * Send initial message to all players.
	 * @param playerList
	 * @param chip no longer used!
	 */
	public static void init(List<Player> playerList, int chip) {
		// TODO Auto-generated method stub
		for(Player player : playerList) {
			PrintWriter pw = null;
			try {
				pw = IOHelper.getWriter(player.getSocket());
				pw.println(player.getInitMsg());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		}
	}
	
	public static void updateView(List<Player> playerList, String massage) {
		for(Player player : playerList) {
			PrintWriter pw = null;
			try {
				pw = IOHelper.getWriter(player.getSocket());
				pw.println(massage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		}
	}
	
	public static void updateView(Player player, String massage) {
			PrintWriter pw = null;
			try {
				pw = IOHelper.getWriter(player.getSocket());
				pw.println(massage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
	}
	
	public static void deal(Player player, int sbIndex) {
		List<Card> poket = player.getCards();
		StringBuilder builder = new StringBuilder();
		builder.append("deal ");
		builder.append(player.getSocket().getPort() + " ");
		builder.append(poket.get(0) + " ");
		builder.append(poket.get(1) + " ");
		builder.append(sbIndex);
		
		PrintWriter pw = null;
		try {
			pw = IOHelper.getWriter(player.getSocket());
			pw.println(builder.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}
	
	public static void yourTurn(Player player, String availableAction) {
		PrintWriter pw = null;
		try {
			pw = IOHelper.getWriter(player.getSocket());
			StringBuilder builder = new StringBuilder();
			builder.append("yt ");
			builder.append(availableAction);
			pw.println(builder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sayGoodbye(List<Player> playerList) {
		// TODO Auto-generated method stub
		for(Player player : playerList) {
			PrintWriter pw = null;
			try {
				pw = IOHelper.getWriter(player.getSocket());
				pw.println("bye");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}
		}
	}

}
