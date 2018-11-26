#!/bin/bash
rmiregistry -J-Djava.class.path=file:/home/matthew/Programming/Java/RMI/bin/ &
echo "Running rmiregistry, hit enter key to kill"
read x
kill $!
echo "Killed rmiregistry"
