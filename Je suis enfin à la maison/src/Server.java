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
	private static String serverAddress = "127.0.0.1";
	private static int serverPort = 5000;

	public static void main(String[] args) throws Exception {

		createTestFiles();
		userInformation = importData("users.txt", userInformation);
		messageHistory = importData("historic.txt", messageHistory);
		clientNumber = userInformation.size();

		// Initialize Server Socket
		listener = new ServerSocket();
		listener.setReuseAddress(true);
		InetAddress serverIP = InetAddress.getByName(serverAddress);
		listener.bind(new InetSocketAddress(serverIP, serverPort));

		// Initial Server Message
		System.out.format("The Server is running on %s:%d%n", serverAddress, serverPort);

		try {
			// Accept Clients
			clientList = new ArrayList<ClientHandler>();
			while (true) {
				ClientHandler client = new ClientHandler(listener.accept());
				clientList.add(client);
				client.start();
			}

		} finally {
			listener.close();
		}

	}
	
	private static void notifyClients(Message message, ClientHandler sender) {
		for (ClientHandler client: clientList) {
			if (!sender.equals(client)) client.broadcast(message);
		}
	}

	// Exports Data from a given arrayList and transfers it into an Serialized file
	// of type T
	private static void createTestFiles() throws Exception {
		// Users
		// Initialize streams
		FileOutputStream fileOutputStream = new FileOutputStream("users.txt");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

		User user = new User("Jesus", "Jesus", 0);
		User user1 = new User("admin", "password", 1);
		User user2 = new User("Joe", "who", 2);
		User user3 = new User("Bob", "burger", 3);

		objectOutputStream.writeObject(4);
		objectOutputStream.flush();
		objectOutputStream.writeObject(user);
		objectOutputStream.flush();
		objectOutputStream.writeObject(user1);
		objectOutputStream.flush();
		objectOutputStream.writeObject(user2);
		objectOutputStream.flush();
		objectOutputStream.writeObject(user3);
		objectOutputStream.flush();

		// Messages
		// Initialize streams
		FileOutputStream fileOutputStream1 = new FileOutputStream("historic.txt");
		ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(fileOutputStream1);
		Message msg = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg1 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg2 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg3 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg4 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg5 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg6 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg7 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg8 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg9 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg10 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg11 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg12 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg13 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg14 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg15 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg16 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg17 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg18 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg19 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg20 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");
		Message msg21 = new Message("Joe", "127.0.0.1", "5000", LocalDate.now(), LocalTime.now(), "test");

		objectOutputStream1.writeObject(22);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg1);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg2);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg3);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg4);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg5);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg6);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg7);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg8);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg9);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg10);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg11);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg12);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg13);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg14);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg15);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg16);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg17);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg18);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg19);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg20);
		objectOutputStream1.flush();
		objectOutputStream1.writeObject(msg21);
		objectOutputStream1.flush();

		objectOutputStream.close();
		objectOutputStream1.close();

	}

	// Imports Data from a given Serialized file and transfers it into an arrayList
	// of type T
	private static <T> ArrayList<T> exportData(String file, ArrayList<T> list)
			throws IOException, ClassNotFoundException {
		// Initialize streams
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

		// Export the number of objects
		int dataQuantity = list.size();
		// Export Data
		try {
			objectOutputStream.writeObject(dataQuantity);
			objectOutputStream.flush();
			for (int i = 0; i < dataQuantity; i++) {
				objectOutputStream.writeObject(list.get(i));
				objectOutputStream.flush();
			}
		} catch (EOFException e) {
			System.out.println("Something went wrong");
		}

		objectOutputStream.close();
		return list;
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

		public Message(String username, String ipAddress, String port, String msg) {
			this.username = username;
			this.ipAddress = ipAddress;
			this.port = port;
			this.date = LocalDate.now();
			this.time = LocalTime.now();
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
		private int userId;

		public User(String username, String password, int userId) {
			this.username = username;
			this.password = password;
			this.userId = userId;
		}

		// Getters and setters
		public String getPassword() {
			return password;
		}

		public String getUsername() {
			return username;
		}
		
		public int getUserId() {
			return userId;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setUsername(String username) {
			this.username = username;
		}
		
		public void setUserId(int userId) {
			this.userId = userId;
		}

	}

	// ClientHandler to take care of each individual client
	private static class ClientHandler extends Thread {

		private Socket socket;
		private int clientNumber;
		private DataInputStream inputStream;
		private DataOutputStream outputStream;

		public ClientHandler(Socket socket) {
			this.socket = socket;
			initializeStreams();
			System.out.println("New connection with client at" + socket);
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
			try {
				// Initialize
				String[] credentials = new String[2];

				// Take Username and Password
				credentials[0] = inputStream.readUTF();
				credentials[1] = inputStream.readUTF();

				// Find username
				for (User user : userInformation) {
					if (user.getUsername().equals(credentials[0])) {
						this.clientNumber = user.getUserId();
						// Verify Password
						return user.getPassword().equals(credentials[1]);
					}
				}

				// Create User
				User user = new User(credentials[0], credentials[1], clientNumber++);
				userInformation.add(user);
				this.clientNumber = user.getUserId();

			} catch (IOException e) {
				System.out.println(e.getMessage());
				return false;
			}

			return true;
		}

		private void initialConnection() throws IOException {
			while (!verifyCredentials()) {
				outputStream.writeBoolean(false);
			}
			outputStream.writeBoolean(true);

			sendMessageHistory();
			outputStream.writeUTF("Hello from server - you are client#" + clientNumber);
		}

		private void sendMessageHistory() {
			try {
				// Give the client the 15 latest messages or the amount of messages
				int numberIterations = Math.max(messageHistory.size() - 15, 0);

				for (int i = messageHistory.size() - 1; i >= numberIterations; i--) {
					outputStream.writeUTF(messageHistory.get(i).toString());
				}

			} catch (IOException e) {
				System.out.println("Error Handling client# " + clientNumber + ": " + e);
			}
		}

		private void broadcast(Message message) {
			try {
				outputStream.writeUTF(message.toString());
			} catch (IOException e) {
				System.out.println("Could not send message : " + message + "to client#" + clientNumber);
			}

		}
		
		// Say a welcome message to the client
		public void run() {

			try {
				initialConnection();
				
				while (true) {
					String messageString = inputStream.readUTF();
					Message message = new Message(userInformation.get(this.clientNumber).getUsername(), serverAddress, Integer.toString(serverPort), messageString);
					notifyClients(message, this);
				}
 			} catch (IOException e) {

			} finally {
				try {
					socket.close();
				} catch (Exception e) {

				}
			}

		}

	}

}
