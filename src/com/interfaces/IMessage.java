package com.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IMessage extends Remote {

    Bool registerUsername(String username) throws RemoteException;
    Bool sendMessage(String fromUsername, String toUsername, String message) throws RemoteException;
    String[] getMessages(String toUsername) throws RemoteException;

	// String[] getLastName(String first) throws RemoteException;
	// void registerName(String first, String last) throws RemoteException;
	// String[] getFullList() throws RemoteException;
}