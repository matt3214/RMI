#!/bin/bash


rm -R bin/com/

javac -d bin -classpath .:bin/ src/com/interfaces/*.java src/com/client/MessageClient.java src/com/server/MessageServer.java 
