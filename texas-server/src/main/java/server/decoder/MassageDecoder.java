package server.decoder;

import java.io.BufferedReader;

import server.Game;
import server.Player;
import server.utils.IOHelper;

public class MassageDecoder {
	private static String[] COMMANDS = {"bet", "raise", "call", "check", "all-in", "fold"};
	public static void process(Game game, Player player) {
		BufferedReader br = null;
		try{
			br = IOHelper.getReader(player.getSocket());
			String msg = br.readLine();
			decode(game, msg, player);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void decode(Game game, String msg, Player player) {
		System.out.println("Message: " + msg);
		String[] msgArr = msg.split(" ");

		String commond = msgArr[0];
		if(commond.equals(MassageDecoder.COMMANDS[0])){
			game.bet(player, Integer.parseInt(msgArr[1]));
		}
		if(commond.equals(MassageDecoder.COMMANDS[1])){
			game.raise(player, Integer.parseInt(msgArr[1]));
		}
		if(commond.equals(MassageDecoder.COMMANDS[2])){
			game.call(player);
		}
		if(commond.equals(MassageDecoder.COMMANDS[3])){
			game.check(player);
		}
		if(commond.equals(MassageDecoder.COMMANDS[4])){
			game.allIn(player);
		}
		if(commond.equals(MassageDecoder.COMMANDS[5])){
			game.fold(player);
		}
	}
	
}
