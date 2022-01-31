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

		createTestFiles();
		userInformation = importData("users.txt", userInformation);
		messageHistory = importData("historic.txt", messageHistory);
		// Socket information
		String serverAdress = "127.0.0.1";
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
				ClientHandler client = new ClientHandler(listener.accept(), clientNumber++);
				client.start();
			

		} finally {
			listener.close();
		}

	}

	// Exports Data from a given arrayList and transfers it into an Serialized file
	// of type T
	private static void createTestFiles() throws Exception{
		//Users
		// Initialize streams
		FileOutputStream fileOutputStream = new FileOutputStream("users.txt");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

		User user = new User("Jesus", "Jesus");
		User user1 = new User("admin", "password");
		User user2= new User("Joe", "who");
		User user3 = new User("Bob", "burger");

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
		
		
		//Messages
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


		public User(String username, String password) {
			this.username = username;
			this.password = password;
		}

		// Getters and setters
		public String getPassword() {
			return password;
		}

		public String getUsername() {
			return username;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public void setUsername(String username) {
			this.username = username;
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
			try {
				String[] credentials =  inputStream.readUTF().split(" ");
				
				//Find username
				for (User x: userInformation) {
					if (x.getUsername().equals(credentials[0])) {
						//Verify Password
						return x.getPassword().equals(credentials[1]);
					}
				}
				
				//Create User
				User user = new User(credentials[0], credentials[1]);
				userInformation.add(user);
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return false;
			}
			
			return true;
		}

		// Say a welcome message to the client
		public void start() {
			try {
				// TODO Give the client the 15 latest messages
				for (int i=messageHistory.size() - 1; i > messageHistory.size() - 16 ; i-- ) {
					outputStream.writeUTF(messageHistory.get(i).toString());
				}
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
