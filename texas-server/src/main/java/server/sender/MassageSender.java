package server.sender;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import server.Game;
import server.Player;
import server.utils.IOHelper;

public class MassageSender {

	public static void init(PrintWriter pw, int chip) {
		// TODO Auto-generated method stub
		pw.println("init " + chip);
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

}
