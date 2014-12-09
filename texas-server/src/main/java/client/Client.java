package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	private String host = "127.0.0.1";
	private int port = 8900;
	private Socket socket;
	
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
			BufferedReader localBr = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			
			while((msg = localBr.readLine())!=null){
				pw.println(msg);
				
				if(msg.equals("bye"))
					break;				
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
				while(true) {
					System.out.println(br.readLine());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	public void starTalk(){
		new Thread() {
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
		}.start();
	}
	
	public void starEcho(){
		new Thread() {
			@Override
			public void run() {
				echo();
			}
		}.start();
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Client client = null;
		
		try{
			client = new Client();
		}catch (Exception e){
//			e.printStackTrace();
			System.out.println("init client exception");
		}
		
		try{
			client.starEcho();
			client.starTalk();
		}catch (Exception e){
//			e.printStackTrace();
			System.out.println("run client exception");
		}

		
	}
}
