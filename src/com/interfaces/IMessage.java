package com.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IMessage extends Remote {

    boolean registerUsername(String username) throws RemoteException;
    boolean sendMessage(String fromUsername, String toUsername, String message) throws RemoteException;
    String[] getMessages(String toUsername) throws RemoteException;
   // void ack(String[] messages) throws RemoteException; //TODO

	// String[] getLastName(String first) throws RemoteException;
	// void registerName(String first, String last) throws RemoteException;
	// String[] getFullList() throws RemoteException;
}