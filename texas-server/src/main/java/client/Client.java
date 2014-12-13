package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

import server.Game;

public class Client {
	private String host = "127.0.0.1";
	private int port = 8900;
	private Socket socket;
	
	private static Thread echoThread;
	private static Thread talkThread;
	
	BufferedReader lb = null;
	
	public Client() throws IOException {
		socket = new Socket(host,port);
	}
	
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut,true);
	}
	
	public void talk() throws IOException {
		try {
			PrintWriter pw = getWriter(socket);
			this.lb = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			
			while(true){
				if((msg = this.lb.readLine())!=null){
					pw.println(msg);
					if(msg.equals("bye")){
						break;
					}
				}		
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			try{
				socket.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	public void echo() {
		BufferedReader br = null;
			try {
				br = getReader(socket);
				String str = null;
				while(true) {
					str = br.readLine();
					if(str != null){
						System.out.println("From server: " + str);
						if(str.startsWith("yt"))
							ai_response(str);
					}else {
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					br.close();
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	private void ai_response(String str) {
		// TODO Auto-generated method stub
		String res = ai_simple_response(str.substring(3));
		try {
			System.out.println("To server: " + res);
			PrintWriter pw = getWriter(socket);
			pw.println(res);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String ai_simple_response(String input) {
		int rand = 0;
		String[] avaActionStr = input.split(" ");
		for(int i = 0; i < avaActionStr.length; i++) {
			System.out.print(avaActionStr[i]+ " ");
		}
		System.out.println("\n");
		
		do {
			rand = (int) (Math.random() * 6);
		} while (0 == Integer.parseInt(avaActionStr[rand]) || 4 == rand || 1 == rand);
		
		String response = Game.actionIndex[rand];
		
		//Bet and raise need argument.
		if (rand == 5 || rand == 3) {
			response += " 10";
		}
		return response;
	}

	public void starTalk(){
		talkThread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					talk();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		talkThread.start();
	}
	
	
	
	public void starEcho(){
		echoThread = new Thread() {
			@Override
			public void run() {
				echo();
			}
		};
		echoThread.start();
	}
	
	
	public static void main(String[] args){
		// TODO Auto-generated method stub
		Client client = null;
		
		try{
			client = new Client();
			System.out.println(client.socket.getLocalPort());
		}catch (Exception e){
			System.out.println("init client exception");
		}
		
		try{
			client.starEcho();
			client.starTalk();
		}catch (Exception e){
			System.out.println("run client exception");
		}

		try {
			echoThread.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("end");
		System.exit(0);
		
//		String res = ai_simple_response("0 1 1 1 1 1");
		
//		System.out.println(res);
	}
	
	
}
