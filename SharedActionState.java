import java.net.*;
import java.util.List;
import java.io.*;

public class SharedActionState{
	
	private SharedActionState mySharedObj;
	private String myThreadName;
	private double mySharedVariable;
	
	//private double
	
	private boolean accessing=false; // true a thread has a lock, false otherwise
	private int threadsWaiting=0; // number of waiting writers
	public double myTransferVariable = 5;
	
	private double Client_C_account;
	private double Client_B_account;
	private double Client_A_account;
	//These are from the corresponded states of KKServer

    private static final int ADD = 0;
    private static final int SUBTRACT = 1;
    private static final int TRANSFER = 2;
 

	

// Constructor	
	
	SharedActionState(double[] Client_account) { //were only calling across one as were creating just one thread per client
		Client_C_account = Client_account[0];
		Client_B_account = Client_account[1];
		Client_A_account = Client_account[2];
	}
	
//Attempt to aquire a lock
	
	  public synchronized void acquireLock() throws InterruptedException{
	        Thread me = Thread.currentThread(); // get a ref to the current thread
	        System.out.println(me.getName()+" is attempting to acquire a lock!");	
	        ++threadsWaiting; //adds on the number of clients.
		    while (accessing) {  // while someone else is accessing or threadsWaiting > 0 accessing
		      System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
		      //wait for the lock to be released - see releaseLock() below
		      wait();
		    }
		    // nobody has got a lock so get one
		    --threadsWaiting;
		    accessing = true; //commit to locking
		    System.out.println(me.getName()+" got a lock!"); 
		  }

		  // Releases a lock to when a thread is finished
		  
		  public synchronized void releaseLock() {
			  //release the lock and tell everyone
		      accessing = false;
		      notifyAll();
		      Thread me = Thread.currentThread(); // get a ref to the current thread
		      System.out.println(me.getName()+" released a lock!");
		  }
	
	
    /* The processInput method */

	public synchronized String processInput(String myThreadName, List<String> theInput) { //importing array list util for here, so that we can store string and number for ease of use.
    		System.out.println(myThreadName + " received "+ theInput);
    		String theOutput = null;
			int state = -1;
    		// Check what the client said
    			//Correct request
    		
    				/*  
    				 * 		Ask user if they want to
    				 * 		1.Add
    				 * 		2.Subtract
    				 * 		3.Transfer A-B, B-A.  A sends 1000 to B: A = 0 B = 2000. then prove B-A, A-B. B Sends 1000 to A: A = 2000 B = 0.
    				 */
    		if (myThreadName.contains("ActionServerThread1")) {
    			if (theInput.get(0).toString().contains("ADD")) {  //USE WHOLE NUMBERS ONLY AS WERE DEALING WITH UNITS (per assignment brief)
        			state = ADD;
        			switch (state){
        	    		case ADD:
        	    			//Get the statement going
        	    			System.out.println("your current ballance was: " + Client_A_account); // using this area 
        	    			theOutput = ("you have chosen addition, " + theInput.get(1) + " " + " has been added to your account"); //output statment
        	    			Client_A_account += Integer.valueOf(theInput.get(1)); //parsing here to integer
        	    			System.out.println("your current ballance is now: " + Client_A_account); // using this area 
        	    			System.out.println("Ballance: " + Client_A_account);
        	    			state = -1;
        	    			break;
        					}
        				}
    			else if (theInput.get(0).toString().contains("SUBTRACT")) { //USE WHOLE NUMBERS ONLY AS WERE DEALING WITH UNITS
					state = SUBTRACT;
					switch (state){
    	    		case SUBTRACT:
    	    			//Get the statement going
    	    			//theOutput = ("you have chosen Subtract, " + theInput.get(1) + " " + " has been added to your account");
    	    			Client_A_account -= Integer.valueOf(theInput.get(1)); //parsing here to integer
    	    			System.out.println(Client_A_account);
    	    			state = -1;
    	    			//break;
    				}

					theOutput = ("you have chosen subtraction: " + theInput.get(1) + " has been deducted");
					System.out.println("your current ballence now is " + Client_A_account );//
				}
else if (theInput.get(0).toString().contains("TRANSFER")) {	//USE WHOLE NUMBERS ONLY AS WERE DEALING WITH UNITS
    				// if a transfer occurs I.E A send 500 to B, B technically doesn't have to be directly informed that they received 500, they could simply check their balance through entering ADD 0 to check if their value has changed from prior/none inputs
					//this is an example of a hard coded transfer from A to B
					state = TRANSFER;
					switch (state){
    	    		case TRANSFER:

    	    			
    	    			theOutput = ("you have chosen Transfer, " + theInput.get(1) + " " + " has been transfered out of your account");
    	    			
    	    			Client_A_account -= Double.valueOf(theInput.get(1)); //parsing
    	    			System.out.println("your current ballance is now: " + Client_A_account);

    	    			Client_B_account += Double.valueOf(theInput.get(1));
				
						}		



		
					}
    			
    		}
    		
    		
    		if (myThreadName.contains("ActionServerThread2")) {
    			if (theInput.get(0).toString().contains("ADD")) {
        			state = ADD;
        			switch (state){
        	    		case ADD:
        	    			//Get the action going
        	    			System.out.println("your current ballance is now: " + Client_B_account); // using this area 

        	    			theOutput = ("you have chosen addition, " + theInput.get(1) + " " + " has been added to your account");
        	    			Client_B_account += Integer.valueOf(theInput.get(1)); //parsing here to integer
        	    			System.out.println(Client_B_account);
        	    			state = -1;
        	    			break;
        					}
        				}
    			else if (theInput.get(0).toString().contains("SUBTRACT")) {
					state = SUBTRACT;
					switch (state){
    	    		case SUBTRACT:
    	    			//Get the action going
    	    			theOutput = ("you have chosen Subtract, " + theInput.get(1) + " " + " has been added to your account");
    	    			Client_B_account -= Integer.valueOf(theInput.get(1)); //parsing here to integer
    	    			System.out.println(Client_B_account);
    	    			state = -1;
    	    			//break;
    				}

					theOutput = ("you have chosen subtraction " + theInput.get(1) + "has been deducted");
					System.out.println("your current ballence now is " + Client_B_account );//+ ActionServer(ourSharedActionStateObjectA));
				}
    			else if (theInput.get(0).toString().contains("TRANSFER")) {	

					//this is an example of a hard coded transfer from A to B
					state = TRANSFER;
					switch (state){
    	    		case TRANSFER:

    	    			
    	    			theOutput = ("you have chosen Transfer, " + theInput.get(1) + " " + " has been added to your account");
    	    			
    	    			Client_B_account -= Double.valueOf(theInput.get(1)); //parsing
    	    			System.out.println("your current ballance is now: " + Client_B_account);

    	    			Client_A_account += Double.valueOf(theInput.get(1));
				
						}		



		
					}
    			
    		}
    			System.out.println(theOutput);
    	    		return theOutput;
	
					}

//	public synchronized double getTransferValue() { //create the method for accessing ANY account
//		return this.myTransferVariable; //try and complete this!!!!!!!!!!!!!!!!!
//		
//	}
//	
//	public synchronized double getAccountValue() { //create the method for accessing ANY account
//		return mySharedVariable; //try and complete this!!!!!!!!!!!!!!!!!
//		
//	}
	
//	public synchronized void AddAccountValueTransfer(double value) {
		
	//	mySharedVariable += value;
	//}
	
}
	//here i will do A - B & B - A
// A will transfer 1000 to B and B will check for 2000, B will then send 200 to A, A will check for 2000 and B will check for 0



/*what to change

each of the three client has their own thread where they are able to add, subtract, transfer within their thread as well.



*/

	
	//work on after solving A
	
	
/*	switch(theInput[0]) {
	  case 1:
	    // code block
	    break;
	  case 2:
	    // code block
	    break;
	  case 3:
		    // code block
		  break;
	  case 4:
		    // code block
	  default:
	    // code block
	}
	
	*/
	
	
//	
//	mySharedVariable = mySharedVariable + 20;
//		mySharedVariable = mySharedVariable * 5;
//		mySharedVariable = mySharedVariable / 3;
//	System.out.println(myThreadName + " made the SharedVariable " + mySharedVariable);
//		theOutput = "The Do action completed.  Shared Variable now = " + mySharedVariable;

//else if (myThreadName.equals("ActionServerThread2")) {
//	/*  
//	 * 		Ask user if they want to
//	 * 		Add
//	 * 		Subtract
//	 * 		Transfer B-A, B-C
//	 */
//		mySharedVariable = mySharedVariable - 5;
//		mySharedVariable = mySharedVariable * 10;
//		mySharedVariable = mySharedVariable / 2.5;
//		
//	System.out.println(myThreadName + " made the SharedVariable " + mySharedVariable);
//	theOutput = "The Do action completed.  Shared Variable now = " + mySharedVariable;
//
//}
//	else if (myThreadName.equals("ActionServerThread3")) {
//		
//		mySharedVariable = mySharedVariable - 50;
//		mySharedVariable = mySharedVariable / 2;
//		mySharedVariable = mySharedVariable * 33;
//
//		System.out.println(myThreadName + " made the SharedVariable " + mySharedVariable);
//	theOutput = "The Do action completed.  Shared Variable now = " + mySharedVariable;
//
//	}
//
//	else {System.out.println("Error - thread call not recognised.");}
//}
//else { //incorrect request
//theOutput = myThreadName + " received incorrect request - only understand \"Do my action!\"";
//
//}

//Return the output message to the ActionServer
	
