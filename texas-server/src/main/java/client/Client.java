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
			BufferedReader br = getReader(socket);
			PrintWriter pw = getWriter(socket);
			BufferedReader localBr = new BufferedReader(new InputStreamReader(System.in));
			String msg = null;
			
			System.out.println(br.readLine());
			System.out.println(br.readLine());
			
//			while((msg = localBr.readLine())!=null){
//				pw.println(msg);
//				System.out.println(br.readLine());
//				
//				if(msg.equals("bye"))
//					break;				
//			}
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
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Client().talk();
	}
	
	public String echo(String msg) {
		return msg;
	}
	


}
