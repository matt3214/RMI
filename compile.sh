#!/bin/bash


rm -R bin/com/

javac -d bin -classpath .:bin/ src/com/interfaces/*.java src/com/client/LastGetterClient.java src/com/server/LastGetter.java 