import java.io.*;
import java.net.*;
import java.util.*;


public class Client {

	private static Socket socket;
	private static String serverAddress; //"127.0.0.1"
	private static int port; //5000
	
	public static void main(String[] args) throws Exception {
		
		//Connect to Server
		do {
			takeServerInformation();	
		} while (!testConnection());

		//Initialize Streams
		DataInputStream in = new DataInputStream(socket.getInputStream()); //ce que le socket li
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
		//Take User Information
		String username = takeUserUsername();
		String password = takeUserPassword();
		
		//Send User information to server
		out.writeUTF(username);
		out.writeUTF(password);
		
		//Accepter 16 messages
		for (int i=0; i < 3; i++) {
			System.out.println(in.readUTF());
		}
		
		socket.close();
	}
	
	//Take the server information
	private static void takeServerInformation() {
		
		//Initialize 
		Scanner scanner = new Scanner(System.in);
		boolean validData = false;
		String[] ipString = {"Allo"};
		Integer[] ipInteger = new Integer[4];
		
		//Take Valid Server Address
		while (!validData) {
			validData = true;
			
			//Display Message
			System.out.print("Enter server address: ");
			
			//Take server ip
			serverAddress = scanner.nextLine();
			ipString = serverAddress.split("[.]", 0);
			System.out.println();
			
			//Test 1: ip address has too many .
			if (ipString.length != 4) {
				validData = false;
			}
			
			//Test 2: ip address contains caracters
			try {
				for (int i=0; i<ipString.length - 1; i++) {
					ipInteger[i] = Integer.parseInt(ipString[i]);
				}
			} catch (NumberFormatException e) {
				validData = false;
			}
			
			//Test 3: ip address doesn't excess 255
			if (validData) {
				for (int i=0; i <ipInteger.length - 1;i++) {
					if (ipInteger[i] < 0 || ipInteger[i] > 255) validData = false;
				}
			}
			
			//Display Error Message
			if (!validData) System.out.println("Invalid Server Address.");
		}
		
		validData = false; //Reset variable
		
		//Take Valid Server Port
		while (!validData) {
			validData = true;
			
			//Display Message
			System.out.println("Enter Server Port: ");
			
			//Take server port and Test 1: port is not a number
			try {
				port = Integer.parseInt(scanner.nextLine());
				System.out.println();
			} catch (NumberFormatException e) {
				validData = false;
			}
			
			//Test 2: port is not between valid values
			if ( validData && (port < 5000 || port > 5050)) {
				validData = false;
			}
			
			//Display Error Message
			if (!validData) System.out.println("Invalid Server Port.");
		}
		
	}

	private static String takeUserPassword() {
		//Initialize 
		boolean notValid = true;
		Scanner scanner = new Scanner(System.in);
		String password = null;
		
		while(notValid) {
			//Display Message
			System.out.print("Enter password: ");
			
			//Take username ip
			password = scanner.nextLine();
			System.out.println();
			
			notValid = password == null;
		}
		
		return password;
	}
	
	private static String takeUserUsername() {
		//Initialize 
		boolean notValid = true;
		Scanner scanner = new Scanner(System.in);
		String username = null;
		
		while(notValid) {
			//Display Message
			System.out.print("Enter username: ");
					
			//Take password ip
			username = scanner.nextLine();
			System.out.println();
			
			notValid = username == null;
			System.out.println("USERNAME:" + username);
		}
		
		return username;
	}
	
	private static boolean testConnection() {
		try {
			socket = new Socket(serverAddress, port);
			System.out.format("The server is running on %s:%d%n", serverAddress, port);
			return true;
		} catch (BindException e) {
			System.out.println("Could not connect to server " + serverAddress + ":" + port);
			System.out.println("Please try again.");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
}
