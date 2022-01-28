import java.io.*;
import java.net.*;
import java.util.*;


public class Server {

	private static ServerSocket listener;
	private static ArrayList<ClientHandler> clientList;
	
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
			//Accept Clients
			while (true) {
				ClientHandler client = new ClientHandler(listener.accept(), clientNumber++);
				if (client.verifyCredentials()) {
					clientList.add(client);
				}
			}
			
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
			initializeStreams();
			System.out.println("New connection with client#" + clientNumber + " at " + socket);
		}
		
		private void initializeStreams() {
			try {
				this.inputStream = new DataInputStream(socket.getInputStream());
				this.outputStream = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.out.println("Could not initialize streams for client #" + clientNumber);
			}
		}
		
		private boolean verifyCredentials() {
			//Verify Username and Password
			return true;
		}
		
		//Say a welcome message to the client
		public void start() {
			try {
				//TODO Give the client the 15 latest messages
				outputStream.writeUTF("Hello from server - you are client#" + clientNumber);
			} catch (IOException e) {
				System.out.println("Error Handling client# " + clientNumber + ": " + e);
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("Socket could not be closed.");
				} finally {
					System.out.println("Connection with client# " + clientNumber + " closed");
				}
			}	
		}
	}	
	
	
 }

