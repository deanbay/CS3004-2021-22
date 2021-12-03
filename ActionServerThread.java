
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class ActionServerThread extends Thread {

	
  private Socket actionSocket = null;
  private SharedActionState mySharedActionStateObject;
  private String myActionServerThreadName;

   
  //Setup the thread
  	public ActionServerThread(Socket actionSocket, String ActionServerThreadName, SharedActionState SharedObject) {
	  this.actionSocket = actionSocket;
	  mySharedActionStateObject = SharedObject;
	  myActionServerThreadName = ActionServerThreadName;
  	}

  public void run() {
    try {
      System.out.println(myActionServerThreadName + "" + "initialising.");
      PrintWriter out = new PrintWriter(actionSocket.getOutputStream(), true);
      out.println("welcome to the WLFB bank made by 1820275 (Dean Baythoon), type from: ADD, SUBTRACT, TRANSFER then have a space between your command and ammount");
      
      
      BufferedReader in = new BufferedReader(new InputStreamReader(actionSocket.getInputStream()));
     
      //Create array list then append with the adding to both string and int so that it can detect what needs to be gathered. 
      String tempInput, outputLine; // the strings that will be entered
      List<String> inputLine = new ArrayList<String>(); //using our new array list 
      //i can convert string to object if i'm using a string then integer, just replace "<String>" with "<Object>"
      String newstr, newint;
      
      while ((tempInput = in.readLine()) != null) {
    	  // Get a lock first
    	  System.out.println(tempInput);
    	  newstr = tempInput.replaceAll("[^A-Za-z]+", "");
    	  newint = tempInput.replaceAll("[A-Za-z]+", "").trim(); //trims off the integer

  		  inputLine.add(newstr);
  		  inputLine.add(newint); //adds said integer to list 
  		 
  		
    	  
    	  try { 
    		  mySharedActionStateObject.acquireLock();  //locks aquired
    		  	outputLine = mySharedActionStateObject.processInput(myActionServerThreadName, inputLine); // the output will process the sharedactionstate object 
    		  	out.println(outputLine);
    		  	mySharedActionStateObject.releaseLock();
    			inputLine.clear(); // clears the array list after the input and makes way for the new input. this prevents first entries from being re-entered to again and again.
    		  	//System.out.println(mySharedVariable);
    			//if() {
    				
    			
    			
    			
    	  } 
    	  catch(InterruptedException e) {
    		  System.err.println("Failed to get lock when reading:"+e);
    	  }
      }
       out.close();
       in.close();
       actionSocket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }



}

//when any of the users access their account, a thread lock occurs, once they leave, thread is unlocked
//there can be deadlock, but seeing as I implemented instantaneous inputs, there are no deadlocks occurring

//eventual consistency

