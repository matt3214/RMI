package com.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.File;


public interface IZip extends Remote {
	File[] unzip(File zip) throws RemoteException;

	
}

//
