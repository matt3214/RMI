package com.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IAdder extends Remote {
	String sayHello() throws RemoteException;
	
	int Add(int a, int b) throws RemoteException;


	
}
