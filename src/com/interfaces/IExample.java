package com.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IExample extends Remote {
	String[] getLastName(String first) throws RemoteException;
	void registerName(String first, String last) throws RemoteException;
	String[] getFullList() throws RemoteException;
}
