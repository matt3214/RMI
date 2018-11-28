package com.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.interfaces.IMessage;

public class MessageServer implements IMessage {
	
	class Message {
		private String toUsername;
		private String fromUsername;
		private String messageStr;

		public Message(String fromUsername, String toUsername, String messageStr) {
			this.toUsername = toUsername;
			this.fromUsername = fromUsername;
			this.messageStr = messageStr;
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

	}

	// public ArrayList<String> usernameList = new ArrayList<String>();

	public Map<String, ArrayList<Message>> usernameList = new HashMap<String, ArrayList<Message>>();

	public boolean registerUsername(String username) throws RemoteException {
		
		for (String entry : usernameList.keySet()) {
			if (entry.equals(username)) {
				return true;
			}
		}

		usernameList.put(username, new ArrayList<Message>());
		
		System.out.println("New User: " + username);
		
		return false;
	}
	
	public String[] getNameList() throws RemoteException{
		return usernameList.keySet().toArray(new String[0]);
	}
	
	// Does that user exist already, return true

	/**
	 * 
	 * @return true if message was successfully stored
	 */
	public boolean sendMessage(String fromUsername, String toUsername, String message) throws RemoteException {

		for (String usr : usernameList.keySet()) {
			if (usr.equals(toUsername)) {
				// need to make the usernamelist a map/dict - this way we can add it to the
				// arrayList associated with the toUsername
				usernameList.get(toUsername).add(new Message(fromUsername, toUsername, message));
				return true;
			}
		}

		return false;
	}

	public String[] getMessages(String destinationUsername) throws RemoteException {
		Message[] messages = usernameList.get(destinationUsername).toArray(new Message[0]);
		String[] returnVal = new String[messages.length];
		for (int i = 0; i < messages.length; i++) {
			returnVal[i] = messages[i].fromUsername + ": " + messages[i].getMessage();
		}
		usernameList.get(destinationUsername).clear();

		return returnVal;
	}

	public static void main(String args[]) {
		try {

			System.setProperty("java.rmi.server.hostname", "155.246.171.40");
			
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

}