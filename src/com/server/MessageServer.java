package com.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.interfaces.IExample;

public class MessageServer implements IExample {

    class Message{
        private String toUsername;
        private String fromUsername;
        private String messageStr;

        public Name(String fromUsername, String toUsername, String messageStr){
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
    public Map<String, ArrayList<String>> usernameList = new HashMap<String, ArrayList<String>()>();

    @override
    Bool registerUsername(String username) throws RemoteException{
        for(String usr : usernameList){
            if(usr.equals(username)){
                return false;
            }
        }

        usernameList.add(username);
        return true;
    }

    Bool sendMessage(String fromUsername, String toUsername, String message) throws RemoteException{
        Message msgObj = new Message(fromUsername, toUsername, message);
        int count = 0;
        for(String usr : usernameList){
            if(usr.equals(toUsername)){
                //need to make the usernamelist a map/dict - this way we can add it to the arrayList associated with the toUsername
                count++;
            }
        }
        if(count == 0){
            return false;
        }
    }

}