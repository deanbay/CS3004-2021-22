import java.net.*;
import java.io.*;





public class ActionServer {
  public static void main(String[] args) throws IOException {

	ServerSocket ActionServerSocket = null;
    boolean listening = true;
    String ActionServerName = "ActionServer";
    int ActionServerNumber = 4545;
    
    double SharedVariable = 100; // this can be the money as it is held here, make the doubles for clients A,B,C
    							// if a transfer occurs I.E A send 500 to B, B dosent have to be directly informed that they recieved 500, they could simply check their ballance through a simple view balance output that then redirects them to the do able actions.

    /*
     *  can we use multiple variables relating to each of the clients (a,b,c)
     *  using setters and getters to access the shared action state to handle the clients actions I.E add, subtract, transfer
    
    
    */
    
    //Create the shared object in the global scope...
    
    SharedActionState ourSharedActionStateObject = new SharedActionState(SharedVariable);
        
    // Make the server socket

    try {
      ActionServerSocket = new ServerSocket(ActionServerNumber);
    } catch (IOException e) {
      System.err.println("Could not start " + ActionServerName + " specified port.");
      System.exit(-1);
    }
    System.out.println(ActionServerName + " started");

    //Got to do this in the correct order with only four clients!  
    //Can automate this...
    
    while (listening){
      new ActionServerThread(ActionServerSocket.accept(), "ActionServerThread1", ourSharedActionStateObject).start();
      new ActionServerThread(ActionServerSocket.accept(), "ActionServerThread2", ourSharedActionStateObject).start();
      new ActionServerThread(ActionServerSocket.accept(), "ActionServerThread3", ourSharedActionStateObject).start();
      new ActionServerThread(ActionServerSocket.accept(), "ActionServerThread4", ourSharedActionStateObject).start();
      System.out.println("New " + ActionServerName + " thread started.");
    }
    ActionServerSocket.close();
  }
}



// For Assignment: we will be storing the shared varibales to each of the respective account

// server holds the money