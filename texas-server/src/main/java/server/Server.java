package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private int port = 8900;
	private ServerSocket serverSocket;
	private Table table;
	
	public Server() throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("server is runnig");
	}

	public void service() {
		Table table = Table.getTableInstance();
		
		while(true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.out.println("New player accepted-->" + socket.getInetAddress()+":"+socket.getPort());
				table.addPlayer(new Player(table.inChip, socket));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//table full

			int seatNumber = table.seatNumber;
			int playerNumber = table.getPlayerNumber();
			if(seatNumber == playerNumber){
				break;
			}
		}
		
		table.startGame();		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Server().service();
	}

}
