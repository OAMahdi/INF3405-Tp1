import java.io.*;
import java.net.*;
import java.util.*;


public class Client {

	private static Socket socket;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String serverAdress = "192.168.2.15";
		int port = 5000;
		
		socket = new Socket(serverAdress, port);
		
		System.out.format("The server is running on %s:%d%n", serverAdress, port);
		
		DataInputStream in = new DataInputStream(socket.getInputStream()); //ce que le socket li
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF("Allo dit moi la meme affaire");
		String welcomingMessage = in.readUTF();
		System.out.println(welcomingMessage);
		socket.close();
	}

}
