import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

	private static ServerSocket listener;
	
	public static void main(String[] args) throws Exception {
		//Keep track of the number of clients
		int clientNumber = 0;
		
		//Socket information
		String serverAdress = "192.168.2.15";
		int serverPort = 5000;
		
		//Initialize Server Socket
		listener = new ServerSocket();
		listener.setReuseAddress(true);
		InetAddress serverIP = InetAddress.getByName(serverAdress);
		listener.bind(new InetSocketAddress(serverIP, serverPort));
		
		//Initial Server Message
		System.out.format("The Server is running on %s:%d%n", serverAdress, serverPort);
		
		try {
			//Do the thing
			Socket clientSocket = listener.accept();
			clientNumber++;
			DataOutputStream output = 
			
			String message = input.readUTF();
			output.writeUTF(message);
			
		} finally {
			listener.close();
		}
		
		
	}
	
	
	//ClientHandler to take care of each individual client
	private static class ClientHandler extends Thread {
		
		private Socket socket;
		private int clientNumber;
		private DataInputStream inputStream;
		private DataOutputStream outputStream;
		
		public ClientHandler(Socket socket, int clientNumber) {
			this.socket = socket;
			this.clientNumber = clientNumber;
			this.inputStream = new DataInputStream(socket.getInputStream());
			this.outputStream = new DataOutputStream(socket.getOutputStream());
			System.out.println("New connection with client#" + clientNumber + " at " + socket);
		}
		
		public void start() {
			try {
				DataOutputStream out = new DataOutputStream(socket.getOutputStream()); //Ce que le socket ecrit
				out.writeUTF("Hello from server - you are client#" + clientNumber);
			} catch (IOException e) {
				System.out.println("Error Handling client# " + clientNumber + ": " + e);
			} finally {
				
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("Couldn't close a socket, what's going on?");
				}
				System.out.println("Connection with client# " + clientNumber + " closed");
			}	
		}
	}	
 }
}
