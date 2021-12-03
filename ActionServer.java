import java.net.*;
import java.io.*;





public class ActionServer {
	
	
  public static void main(String[] args) throws IOException {

	ServerSocket ActionServerSocket = null;
    boolean listening = true;
    String ActionServerName = "ActionServer";
    int ActionServerNumber = 4545;
    SharedActionState account;
// if a transfer occurs I.E A send 500 to B, B doesn't have to be directly informed that they received 500, they could simply check their balance through ADD 0 to check if their value has changed from prior/none inputs

 // this can be the money as it is held here, make the doubles for clients A,B,C
     double Client_C_account = 1000;
     double Client_B_account= 1000;
     double Client_A_account = 1000;
     
     double[] accounts = {Client_C_account, Client_B_account, Client_A_account};
     account = new SharedActionState(accounts); // from a single shared action, this allows the clients to then transfer between themselves and the other clients
     /*
      *  can we use multiple variables relating to each of the clients (a,b,c)
      *  using setters and getters to access the shared action state to handle the clients actions I.E add, subtract, 		 transfer
     
     */
     
     //Create the shared object in the global scope...
    
   
    
    
    
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
      new ActionServerThread(ActionServerSocket.accept(), "ActionServerThread1", account).start(); // pass through socket, then the name, then the shared action state AB or C
      System.out.println("New " + ActionServerName + " thread started.");

      
      new ActionServerThread(ActionServerSocket.accept(), "ActionServerThread2",account ).start();
      System.out.println("New " + ActionServerName + " thread started.");
      
      
      
      new ActionServerThread(ActionServerSocket.accept(), "ActionServerThread3", account).start();
      System.out.println("New " + ActionServerName + " thread started.");
      
     
    
    }
    ActionServerSocket.close();
  }
  
		
  }






// For Assignment: we will be storing the shared variables to each of the respective account
// server holds the money

//Hope you enjoy viewing my code, countless hours and many head scratches were made during the making of this program
