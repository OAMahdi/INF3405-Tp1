import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Server {

	private static ServerSocket listener;
	private static ArrayList<ClientHandler> clientList;
	private static ArrayList<User> userInformation;
	private static int clientNumber;

	public static void main(String[] args) throws Exception {

		importUsers();
		initializeHistory();
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
	
	private static void initializeHistory() throws Exception{
		FileOutputStream fileOutputStream = new FileOutputStream("historic.txt");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
		
		Message msg = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "Mama");
		Message msg1 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "I just killed a man");
		Message msg2 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "Put a gun up to his head");
		Message msg3 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "Pulled my trigger now he's dead");
		Message msg4 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "Mama");
		Message msg5 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "Life had just begun");
		Message msg6 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "And now I've gone and");
		Message msg7 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "Thrown it all");
		Message msg8 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "Away");
		Message msg9 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "Mama");
		Message msg10 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "(Let the wind blow)");
		Message msg11 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "I don't wanna die");
		Message msg12 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "Sometimes wish I'd never been born at all");
		Message msg13 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "(Guitar Solo)");
		Message msg14 = new Message("Joe", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "(Guitar Solo Continues)");
		Message msg15 = new Message("Jesus", "192.168.2.15", "5000", LocalDate.now(), LocalTime.now(), "Yo my bad je pensais qu'on faisait ca ya 3 jours");
		
		int numberMessages = 16;
		
		objectOutputStream.writeObject(numberMessages);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg1);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg2);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg3);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg4);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg5);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg6);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg7);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg8);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg9);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg10);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg11);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg12);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg13);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg14);
		objectOutputStream.flush();
		objectOutputStream.writeObject(msg15);
		objectOutputStream.flush();
		
		objectOutputStream.close();
		
	}

	private static void importUsers() throws IOException, ClassNotFoundException {
		// Initialize streams
		FileInputStream fileInputStream = new FileInputStream("users.txt");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

		// Import the number of users
		int userNumber = (Integer) objectInputStream.readObject();
		// Import Users
		userInformation = new ArrayList<User>();
		try {
			for (int i = 0; i < userNumber; i++) {
				User user = (User) objectInputStream.readObject();
				userInformation.add(user);
			}
		} catch (EOFException e) {
			System.out.println("Somehow reached the end of users.txt...");
		}

		objectInputStream.close();
	}

	//Message builder
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
			// Verify Username and Password
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
