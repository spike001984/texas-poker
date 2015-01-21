package client.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Communicator {
	
	private String host = "127.0.0.1";
	private int port = 8900;
	
	private Socket socket;
	
	private BufferedReader input;
	private PrintWriter output;
	
	private Communicator(){
		
	}
	
	private void init() {
		try {
			socket = new Socket(host, port);
			input = getReader(socket);
			output = getWriter(socket);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Communicator getInstance() {
		Communicator result = new Communicator();
		result.init();
		return result;
	}
	
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut,true);
	}
	
	public void send(String msg) {
		output.println(msg);
	}
	
	public String recive() {
		String msg = null;
		try {
			msg = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
}
