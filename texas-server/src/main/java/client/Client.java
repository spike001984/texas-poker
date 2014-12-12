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
						System.out.println(str);
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
	}
}
