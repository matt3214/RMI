package com.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.interfaces.IMessage;

public class MessageServer implements IMessage {
	class User {
		private String username;
		private boolean loggedOn = false;

		public User(String username) {
			this.username = username;
			loggedOn = true;
		}

		public String getUsername() {
			return username;
		}

		public boolean isLoggedOn() {
			return loggedOn;
		}

		public void logOff() {
			loggedOn = false;
		}

		public void logOn() {
			loggedOn = true;
		}
	}

	class Message {
		private String toUsername;
		private String fromUsername;
		private String messageStr;
		private int uid;
		private long timestamp;

		public Message(String fromUsername, String toUsername, String messageStr, int uid, long timestamp) {
			this.toUsername = toUsername;
			this.fromUsername = fromUsername;
			this.messageStr = messageStr;
			this.uid = uid;
			this.timestamp = timestamp;
		}

		public String getToUsername() {
			return toUsername;
		}

		public String getFromUsername() {
			return fromUsername;
		}

		public String getMessage() {
			return messageStr;
		}

		public int getUID() {
			return uid;
		}

		public long getTimeStamp() {
			return timestamp;
		}

	}

	// public ArrayList<String> usernameList = new ArrayList<String>();

	public Map<User, ArrayList<Message>> usernameList = new HashMap<User, ArrayList<Message>>();
	public int idCount;

	/**
	 * Get user with name
	 * 
	 * @return user, or null if that user is not in the list
	 */
	public User getUserWithName(String username) {
		for (User u : usernameList.keySet()) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}

	// So we have unique id's

	/**
	 * @return -1 if username taken and logged on currently 0 if new user 1 if
	 *         username taken but not previously logged on
	 */
	public int registerUsername(String username) throws RemoteException {

		User user = getUserWithName(username);
		if (user == null) {
			usernameList.put(new User(username), new ArrayList<Message>());

			System.out.println("New User: " + username);
			return 0;
		}

		if (user.loggedOn) {
			return -1;
		} else {
			// Log on as that person
			user.logOn();
			System.out.println("Welcome back: " + username);
			return 1;
		}

	}

	public String[] getNameList() throws RemoteException {
		ArrayList<String> usernames = new ArrayList<String>();
		for (User u : usernameList.keySet()) {
			if (u.isLoggedOn()) {
				usernames.add(u.getUsername());
			}
		}
		return usernames.toArray(new String[0]);
	}

	// Does that user exist already, return true

	/**
	 * 
	 * @return true if message was successfully stored
	 */
	public boolean sendMessage(String fromUsername, String toUsername, String message) throws RemoteException {

		for (User usr : usernameList.keySet()) {
			if (usr.getUsername().equals(toUsername)) {
				// need to make the usernamelist a map/dict - this way we can add it to the
				// arrayList associated with the toUsername
				usernameList.get(getUserWithName(toUsername))
						.add(new Message(fromUsername, toUsername, message, idCount++, System.currentTimeMillis()));
				return true;
			}
		}
		return false;
	}

	public String[] getMessages(String destinationUsername) throws RemoteException {
		Message[] messages = usernameList.get(getUserWithName(destinationUsername)).toArray(new Message[0]);
		String[] returnVal = new String[messages.length];
		for (int i = 0; i < messages.length; i++) {
			returnVal[i] = messages[i].fromUsername + ": " + messages[i].getMessage() + "|" + messages[i].uid;
		}

		return returnVal;
	}

	public void logOff(String username) throws RemoteException {
		for (User u : usernameList.keySet()) {
			if (u.getUsername().equals(username)) {
				u.logOff();
			}
		}
	}

	public static void main(String args[]) {

		System.out.println("Enter the ip of the host computer");
		String ip = new Scanner(System.in).nextLine();
		try {

			System.setProperty("java.rmi.server.hostname", ip);

			MessageServer obj = new MessageServer();

			// Create stub
			IMessage stub = (IMessage) UnicastRemoteObject.exportObject(obj, 0);

			// Locate Registry
			Registry registry = LocateRegistry.getRegistry();

			// Print registry port
			System.out.println(Registry.REGISTRY_PORT);

			// Bind stub to registry with name "Adder"
			registry.bind("MessageServer", stub);

			System.err.println("Server ready");

		} catch (Exception e) {
			System.out.println("Messaging err: " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		//
	}

	public void ack(String username, int[] ids) throws RemoteException {
		ArrayList<Message> list = usernameList.get(getUserWithName(username));
		ArrayList<Message> remove = new ArrayList<Message>();
		for (Message m : list) {
			for (int i = 0; i < ids.length; i++) {
				if (ids[i] == m.uid) {
					remove.add(m);
				}
			}
		}

		list.removeAll(remove);
	}

}