first compile all source files with 		bash compile.sh
then start the RMI Registry server with 	bash runRegistry.sh
then start the LastNameServer with 		bash runServer.sh
then start a LastNameGetter Client with		bash runClient.sh

cleanup in reverse order closing the rmiregistry process by hitting enter in the shell



FOR me:
    RMI Registry = C:\cpe545\RMI\bin>rmiregistry -J-Djava.class.path=file:/cpe545/RMI/bin/ &
    Start Server = C:\cpe545\RMI\bin>java -classpath . com.server.LastGetter

