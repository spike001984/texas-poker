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
	
	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut,true);
	}

	public void service() {
		while(true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.out.println("New connection accepted" + socket.getInetAddress()+":"+socket.getPort());
				BufferedReader br = getReader(socket);
				PrintWriter pw = getWriter(socket);
				
				String msg = null;
				while((msg = br.readLine()) != null) {
					System.out.println(msg);
					pw.println("get it:" + msg);
					if(msg.equals("bye"))
						break;
				}
			} catch (IOException e){
				e.printStackTrace();
			} finally {
				try{
					if(socket!=null)
						socket.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Server().service();
	}

}
