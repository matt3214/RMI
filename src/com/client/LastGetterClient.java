package com.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.interfaces.IExample;
import java.util.Scanner;
public class LastGetterClient {
	private static Scanner input = new Scanner(System.in);
	public static void main(String arg[]) {

		try {
			// Getting the registry
			Registry registry = LocateRegistry.getRegistry(null);
			
			System.out.println(registry.list().length);
			
			// Looking up the registry for the remote object
			IExample stub = (IExample) registry.lookup("Example");
			
			String behavior = "";

			do{
				//Loop until a period is sent
			do{
				//Check registration loop, or move on to listing name request

				System.out.println("Register new student by typing \"[register]\"\ntype a first name to get last names from server\ntype \"[list]\" to list all names on server\nor type a single . to quit");
				behavior = input.nextLine();
				if
				if(behavior.contains("[register]")){
					System.out.println("Type the students first name:");
					String f = input.nextLine();
					System.out.println("Type the students last name:");
					String l = input.nextLine();
					stub.registerName(f,l);
					System.out.println("Registered: " + f + " " + l + " successfully.");
				}else if(behavior.contains("[list]")){
					String[] names = stub.getFullList();
					System.out.println("Namelist contains "+names.length+" names: ");
					for(String name: names){
						System.out.println(name);
					}
				}else{
					System.out.println("Checking nameserver for that name ");
				}
			}while(behavior.contains("["));


			String first = behavior;

			String[] lastNames = stub.getLastName(first);
			if(lastNames.length>0 && !lastNames[0].equals("Not Found")){
				System.out.println("Found: " + lastNames.length +" students with that last name;");
				for(String lastName : lastNames){
					System.out.println(first + " " + lastName); 
				}
			}else{
				System.out.println("Student with that first name not found");
			}

		}while(!behavior.equals("."));

			System.out.println("Quitting");



			// System.out.println("Remote method invoked");
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}