package com.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.interfaces.IMessage;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class MessageClient {
	private static Scanner input = new Scanner(System.in);
	public static String username;
	public static String host = "";

	public static void main(String arg[]) {

		try {
//			System.setProperty("java.security.policy", "file:///test.policy");
//			
//			if (System.getSecurityManager() == null) {
//				System.setSecurityManager(new SecurityManager());
//			}

            System.out.println("Enter the server IP address:");
            host = input.nextLine();

			// Getting the registry
			Registry registry = LocateRegistry.getRegistry(host, 1099);

			//System.out.println(registry.list().length);
			//System.out.println(registry.list()[0]);

			// Looking up the registry for the remote object
			IMessage stub = (IMessage) registry.lookup("MessageServer");

			System.out.println(stub.toString());
			int taken = -1;

			do {
				System.out.println("enter your username:");
				username = input.nextLine();
				taken = stub.registerUsername(username);
				if (taken == -1) {
					System.out.print("username already taken, please ");
				}else if(taken==1) {
					System.out.print("Welcome back ");
				}else {
					System.out.print("Welcome ");
				}
			} while (taken != 0 && taken != 1);

			System.out.println(" to the chat client " + username + "!");

			String behavior = "";

			boolean running = true;
			boolean doInstructions = true;
			do {
				if (doInstructions) {
					System.out.println("Check for messages by hitting enter");
					System.out.println("or type a username to send a message to");
					System.out.println("or type /list to display a list of all connected users");
					System.out.println("or type a single . to quit");
					doInstructions = false;
				}
				behavior = input.nextLine();

				if (behavior.equals(".")) {
					running = false;
					break;
				}

				if (behavior.length() > 0) {
					doInstructions = true;
					if (behavior.contains("/list")) {
						System.out.println("Current users connected: ");
						for (String name : stub.getNameList()) {
							System.out.println(name);
						}
					} else {
						// Must have been a username
						System.out.println("Please enter your message");

						if (stub.sendMessage(username, behavior, input.nextLine())) {
							System.out.println("Bon Voyage!");
						} else {
							System.out.println("Message not sent, username: " + behavior + " not found");
						}
					}
				} else {
					String[] messagesRecieved = stub.getMessages(username);
					int[] ids = new int[messagesRecieved.length];

					for (int i = 0; i < messagesRecieved.length; i++) {
						String[] messageSplit = messagesRecieved[i].split("[|]");
						System.out.println(messageSplit[0]);
						ids[i] = Integer.parseInt(messageSplit[1]);
					}
					stub.ack(username, ids);

				}

			} while (running);
			stub.logOff(username);
			// System.out.println("Remote method invoked");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}