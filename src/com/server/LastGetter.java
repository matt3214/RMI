package com.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.interfaces.IExample;

public class LastGetter implements IExample {

    class Name{
        private String first;
        private String last;

        public Name(String first, String last){
            this.first = first;
            this.last = last;
        }

        public String getFirst(){
            return first;
        }
        public String getLast(){
            return last;
        }
    }

    public ArrayList<Name> nameList = new ArrayList<Name>();

	@Override
    public String[] getLastName(String first) throws RemoteException{
        String[] list;
        int count = 0;
        for(Name n: nameList){
            if(n.getFirst().equals(first)){
                count++;
            }
        }
        list = new String[count];
        if(list.length == 0){
            return new String[]{"Not Found"};
        }else{
            int i = 0;
            for(Name n: nameList){
            if(n.getFirst().equals(first)){
                list[i] = n.getLast();
                i++;
            }
        }
        }
        return list;
    }

    public void registerName(String first,String last)throws RemoteException{
        nameList.add(new Name(first,last));
    }

    public String[] getFullList()throws RemoteException{
        String[] stringList = new String[nameList.size()];
        int i = 0;
        for(Name n : nameList){
            stringList[i] = n.getFirst() + " " + n.getLast();
            i++;
        }
        return stringList;
    }


	public LastGetter() throws RemoteException {
        nameList.add(new Name("Matthew","Duffy"));
        nameList.add(new Name("Matthew","Josephs"));
    }


	public static void main(String args[]) {
		try {
			LastGetter obj = new LastGetter();
	

			//Create stub
			IExample stub = (IExample) UnicastRemoteObject.exportObject(obj, 0);

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
