package com.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.interfaces.IMessage;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class MessageClient {
	private static Scanner input = new Scanner(System.in);
	public static String username;

	public static void main(String arg[]) {

		try {
			// Getting the registry
			Registry registry = LocateRegistry.getRegistry(null);

			System.out.println(registry.list().length);

			// Looking up the registry for the remote object
			IMessage stub = (IMessage) registry.lookup("MessageServer");

			boolean taken = false;

			do {
				System.out.println("enter your username:");
				username = input.nextLine();
				taken = stub.registerUsername(username);
				if (taken) {
					System.out.print("username already taken, please ");
				}
			} while (taken);

			System.out.println("Welcome to the chat client!");

			String behavior = "";

			boolean running = true;
			boolean doInstructions = true;
			do {
				if (doInstructions) {
					System.out.println("Check for messages by hitting enter");
					System.out.println("or type a username to send a message to");
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
					System.out.println("Please enter your message");

					if (stub.sendMessage(username, behavior, input.nextLine())) {
						System.out.println("Bon Voyage!");
					}else {
						System.out.println("Message not sent, username: " + behavior + " not found");
					}
				} else {
					String[] messagesRecieved = stub.getMessages(username);
					for(int i =0;i<messagesRecieved.length;i++) {
						System.out.println(messagesRecieved[i]);
					}
					
				}

			} while (running);

			// System.out.println("Remote method invoked");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}