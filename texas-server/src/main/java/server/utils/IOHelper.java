package server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class IOHelper {
	public static BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}
	
	public static PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut,true);
	}
}
