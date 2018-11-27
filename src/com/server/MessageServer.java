package com.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.interfaces.IMessage;

public class MessageServer implements IMessage {

    class Message{
        private String toUsername;
        private String fromUsername;
        private String messageStr;

        public Message(String fromUsername, String toUsername, String messageStr){
            this.toUsername = toUsername;
            this.fromUsername = fromUsername;
            this.messageStr = messageStr;
        }

        public String getToUsername(){
            return toUsername;
        }
        public String getFromUsername(){
            return fromUsername;
        }
        public String getMessage(){
            return messageStr;
        }
        
    }

    //public ArrayList<String> usernameList = new ArrayList<String>();
    
    public Map<String, ArrayList<Message>> usernameList = new HashMap<String, ArrayList<Message>>();

    public boolean registerUsername(String username) throws RemoteException{
        
    	for(String entry: usernameList.keySet()){
    			if(entry.equals(username)) {
                return false;
            }
    	}
    	

        usernameList.put(username, new ArrayList<Message>());
        return true;
    }
    //Does that user exist, return true
    
    public boolean sendMessage(String fromUsername, String toUsername, String message) throws RemoteException{
        
        for(String usr : usernameList.keySet()){
            if(usr.equals(toUsername)){
                //need to make the usernamelist a map/dict - this way we can add it to the arrayList associated with the toUsername
                usernameList.get(toUsername).add(new Message(fromUsername, toUsername, message));
                return true;
            }
        }
        
		return false;
    }

	public String[] getMessages(String toUsername) throws RemoteException {
		return null;
	}

}