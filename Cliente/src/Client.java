import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Client {

	private static Socket socket;
	private static String serverAddress = "127.0.0.1"; // "127.0.0.1"
	private static int port = 5000; // 5000

	public static void main(String[] args) throws Exception {

		// Connect to Server
		do {
			takeServerInformation();
		} while (!testConnection());

		// Initialize Streams
		DataInputStream in = new DataInputStream(socket.getInputStream()); // ce que le socket li
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		
		// Send User information to server
		out.writeUTF(takeUserUsername());
		out.writeUTF(takeUserPassword());
		while (!in.readBoolean()) {
			System.out.println("Wrong Password :( Please try again");
			out.writeUTF(takeUserUsername());
			out.writeUTF(takeUserPassword());
		}
		
		in.close();
		out.close();
		//new WritingThread(out).start();
		System.out.println("Socket is +" + socket.isClosed());
		new Thread(new ReadingThread(socket)).start();
		
		/*// Accepter 16 messages
		try {
			for (int i = 0; i < 16; i++) {
				System.out.println(in.readUTF());
			}
		} catch (EOFException e) {
			System.out.println("it happened.");
		} */

		// Ecrire
		while (true);
		
	}

	private static void IHopeThisDoesntWork(DataInputStream stream) throws IOException{
		System.out.println(stream.readUTF());
	}
	
	// Take the server information
	private static void takeServerInformation() {

		// Initialize
		Scanner scanner = new Scanner(System.in);
		boolean validData = false;
		String[] ipString = { "Allo" };
		Integer[] ipInteger = new Integer[4];

		// Take Valid Server Address
		while (!validData) {
			validData = true;

			// Display Message
			System.out.print("Enter server address: ");

			// Take server ip
			serverAddress = scanner.nextLine();
			ipString = serverAddress.split("[.]", 0);

			// Test 1: ip address has too many .
			if (ipString.length != 4) {
				validData = false;
			}

			// Test 2: ip address contains caracters
			try {
				for (int i = 0; i < ipString.length - 1; i++) {
					ipInteger[i] = Integer.parseInt(ipString[i]);
				}
			} catch (NumberFormatException e) {
				validData = false;
			}

			// Test 3: ip address doesn't excess 255
			if (validData) {
				for (int i = 0; i < ipInteger.length - 1; i++) {
					if (ipInteger[i] < 0 || ipInteger[i] > 255)
						validData = false;
				}
			}

			// Display Error Message
			if (!validData)
				System.out.println("Invalid Server Address.");
		}

		validData = false; // Reset variable

		// Take Valid Server Port
		while (!validData) {
			validData = true;

			// Display Message
			System.out.print("Enter Server Port: ");

			// Take server port and Test 1: port is not a number
			try {
				port = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				validData = false;
			}

			// Test 2: port is not between valid values
			if (validData && (port < 5000 || port > 5050)) {
				validData = false;
			}

			// Display Error Message
			if (!validData)
				System.out.println("Invalid Server Port.");
		}

	}

	private static String takeUserPassword() {
		// Initialize
		boolean notValid = true;
		Scanner scanner = new Scanner(System.in);
		String password = null;

		while (notValid) {
			// Display Message
			System.out.print("Enter password: ");

			// Take password
			password = scanner.nextLine();

			notValid = password == "";
			// Display Error Message
			if (notValid)
				System.out.println("Invalid Password.");
		}

		return password;
	}

	private static String takeUserUsername() {
		// Initialize
		boolean notValid = true;
		Scanner scanner = new Scanner(System.in);
		String username = null;

		while (notValid) {
			// Display Message
			System.out.print("Enter username: ");

			// Take username
			username = scanner.nextLine();

			notValid = username == "";
			// Display Error Message
			if (notValid)
				System.out.println("Invalid Username.");
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

	static class WritingThread implements Runnable {
		private DataOutputStream stream;

		public WritingThread(DataOutputStream stream) {
			this.stream = stream;
		}

		@Override
		public void run() {
			Scanner scanner = new Scanner(System.in);
			
			while (true) {
				
				String message = scanner.nextLine();
				try {
					stream.writeUTF(message);
				} catch (IOException e) {
				}
				
			}
		}
	}

	static class ReadingThread implements Runnable {
		private Socket socket;
		private DataInputStream input;
		private DataOutputStream output;

		public ReadingThread(Socket socket) {
			this.socket = socket;
			try {
				System.out.println(socket.isClosed());
				input = new DataInputStream(socket.getInputStream());
				output = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.out.println(e);
			}
			System.out.println("allo, j'existe.");
		}

		@Override
		public void run() {
			while (true) {
			try {
				String message = input.readUTF();
				while (message != null) {
					System.out.println(message);
					message = input.readUTF();
				}
			} catch (IOException e) {
				
			}	
			}
		}
		}
	}

