import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

	private static ServerSocket listener;
	
	public static void main(String[] args) throws Exception {
		int clientNumber = 0;
		String serverAdress = "192.168.2.15";
		int serverPort = 5000;
		
		listener = new ServerSocket();
		listener.setReuseAddress(true);
		InetAddress serverIP = InetAddress.getByName(serverAdress);
		
		listener.bind(new InetSocketAddress(serverIP, serverPort));
		
		System.out.format("The Server is running on %s:%d%n", serverAdress, serverPort);
		
		try {
			
			Socket clientSocket = listener.accept();
			clientNumber++;
			DataInputStream input = new DataInputStream(clientSocket.getInputStream());
			DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
			
			String message = input.readUTF();
			output.writeUTF(message);
			
			/*
			while (true) {
				new ClientHandler(listener.accept(), clientNumber++).start();
			}
			*/
		} finally {
			listener.close();
		}
		
		
	}
	
	
	
	private static class ClientHandler extends Thread {
		
		private Socket socket;
		private int clientNumber;
		
		public ClientHandler(Socket socket, int clientNumber) {
			// TODO Auto-generated constructor stub
			this.socket = socket;
			this.clientNumber = clientNumber;
			System.out.println("New connection with client#" + clientNumber + " at " + socket);
		}
		
		public void start() {
			try {
				DataOutputStream out = new DataOutputStream(socket.getOutputStream()); //Ce que le socket ecrit
				out.writeUTF("Hello from server - you are client#" + clientNumber);
			} catch (IOException e) {
				System.out.println("Error Handling client# " + clientNumber + ": " + e);
			} finally {
				/*
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("Couldn't close a socket, what's going on?");
				}
				System.out.println("Connection with client# " + clientNumber + " closed");
			}
			*/
		}
		
		

	}
	

	}
}
