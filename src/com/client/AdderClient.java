package com.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.interfaces.IAdder;

public class AdderClient {
	public static void main(String arg[]) {

		try {
			// Getting the registry
			Registry registry = LocateRegistry.getRegistry(null);
			
			System.out.println(registry.list().length);
			
			// Looking up the registry for the remote object
			IAdder stub = (IAdder) registry.lookup("Adder");

			// Calling the remote method using the obtained object
			System.out.println(stub.Add(100, 40));

			// System.out.println("Remote method invoked");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}