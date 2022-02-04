import java.io.*;
import java.net.*;
import java.util.*;


public class Client {

	private static Socket socket;
	private static String serverAddress; //"127.0.0.1"
	private static int port; //5000
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		serverAddress = "127.0.0.1";
		port = 5000;
		
		socket = new Socket(serverAddress, port);
		
		System.out.format("The server is running on %s:%d%n", serverAddress, port);
		
		DataInputStream in = new DataInputStream(socket.getInputStream()); //ce que le socket li
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());

		//Accepter 16 messages
		for (int i=0; i < 16; i++) {
			System.out.println(in.readUTF());
		}
		
		socket.close();
	}
	
	//Take the server information
	private static void takeServerInformation() {
		
		//Initialize 
		Scanner scanner = new Scanner(System.in);
		boolean validData = false;
		String[] ipString;
		Integer[] ipInteger = new Integer[4];
		
		//Take Valid Server Address
		while (!validData) {
			validData = true;
			
			//Display Message
			System.out.print("Enter server adress: ");
			
			//Take server ip
			serverAddress = scanner.nextLine();
			ipString = serverAddress.split(".");
			
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

	private static void takeUserInformation() {
		
	}
	
	private static void testConnection() {
		boolean validConnection = false;
		
		try {
			socket = new Socket(serverAddress, port);
		} catch (BindException e) {
			System.out.println("Could not connect to server " + serverAddress + ":" + port);
			System.out.println("Please try again.");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		if (validConnection) {
			takeServerInformation();
		} else {
			takeUserInformation();
		}

	}
	
	

}
