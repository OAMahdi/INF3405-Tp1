import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Server {

	private static ServerSocket listener;
	private static ArrayList<ClientHandler> clientList;
	private static ArrayList<User> userInformation;
	private static ArrayList<Message> messageHistory;
	private static int clientNumber;

	public static void main(String[] args) throws Exception {

		userInformation = importData("users.txt", userInformation);
		messageHistory = importData("historic.txt", messageHistory);
		// Socket information
		String serverAdress = "192.168.2.15";
		int serverPort = 5000;

		// Initialize Server Socket
		listener = new ServerSocket();
		listener.setReuseAddress(true);
		InetAddress serverIP = InetAddress.getByName(serverAdress);
		listener.bind(new InetSocketAddress(serverIP, serverPort));

		// Initial Server Message
		System.out.format("The Server is running on %s:%d%n", serverAdress, serverPort);

		for (User x : userInformation) {
			System.out.println(x.username);
		}
		for (Message x : messageHistory) {
			System.out.println(x);
		}

		try {
			// Accept Clients
			clientList = new ArrayList<ClientHandler>();
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

	// Imports Data from a given Serialized file and transfers it into an arrayList
	// of type T
	private static <T> ArrayList<T> importData(String file, ArrayList<T> list)
			throws IOException, ClassNotFoundException {
		// Initialize streams
		FileInputStream fileInputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

		// Import the number of objects
		int dataQuantity = (Integer) objectInputStream.readObject();
		// Import Data
		list = new ArrayList<T>();
		try {
			for (int i = 0; i < dataQuantity; i++) {
				T type = (T) objectInputStream.readObject();
				list.add(type);
			}
		} catch (EOFException e) {
			System.out.println("Somehow reached the end of users.txt...");
		}
		objectInputStream.close();
		return list;
	}

	// Message builder
	private static class Message implements Serializable {

		private String username;
		private String ipAddress;
		private String port;
		private LocalDate date;
		private LocalTime time;
		private String msg;

		public Message(String username, String ipAddress, String port, LocalDate date, LocalTime time, String msg) {
			this.username = username;
			this.ipAddress = ipAddress;
			this.port = port;
			this.date = date;
			this.time = time;
			this.msg = msg;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "[" + username + " - " + ipAddress + ":" + port + " - " + date + "@"
					+ time.toString().substring(0, 7) + "]: " + msg;
		}
	}

	// User to store the connection information of each user
	private static class User implements Serializable {

		private String username;
		private String password;
		private int clientID;

		public User(String username, String password, int clientID) {
			this.username = username;
			this.password = password;
			this.clientID = clientID;
		}

		// Getters and setters
		public String getPassword() {
			return password;
		}

		public String getUsername() {
			return username;
		}

		public int getClientID() {
			return clientID;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public void setClientID(int clientID) {
			this.clientID = clientID;
		}
	}

	// ClientHandler to take care of each individual client
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
			// TODO Verify Username and Password
			return true;
		}

		// Say a welcome message to the client
		public void start() {
			try {
				// TODO Give the client the 15 latest messages
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
