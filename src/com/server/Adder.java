package com.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.interfaces.IAdder;

public class Adder implements IAdder {

	@Override
	public String sayHello() throws RemoteException {
		return "Hello!";
	}

	@Override
	public int Add(int a, int b) throws RemoteException {
		return a + b;
	}
	@Override
	public File[] unzip(File zip) throws RemoteException{

	}

	public Adder() throws RemoteException {}

	public static void main(String args[]) {
		try {
			Adder obj = new Adder();
	

			//Create stub
			IAdder stub = (IAdder) UnicastRemoteObject.exportObject(obj, 0);

			//Locate Registry
			Registry registry = LocateRegistry.getRegistry();

			//Print registry port
			System.out.println(Registry.REGISTRY_PORT);

			//Bind stub to registry with name "Adder"
			registry.bind("Example", stub);

			System.err.println("Server ready");

		} catch (Exception e) {
			System.out.println("Example err: " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
		//
	}

	/*
	class UnzipFile implements IZIP {
    public static void main(String[] args) throws IOException {

        String fileZip = "src/main/resources/unzipTest/compressed.zip";

        File destDir = new File("src/main/resources/unzipTest");
        byte[] buffer = new byte[1024];

        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }
     
    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
         
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }

    **
    *Returns the list of files stored in zip file 'zip'
	*
    **
    public File[] unzip(File zip) throws RemoteException{
    	//used for file writing
        byte[] buffer = new byte[1024];

        //Stores files to be returned
    	ArrayList<File> fileList = new ArrayList<File>();

    	//Create zip inputstream from zipfile zip
    	ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));

    	//begin looping through files and adding them to the fileList
    	ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(destDir, zipEntry);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();


            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public 
    */

}
