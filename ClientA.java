import java.io.*;
import java.net.*;

public class ClientA {
    public static void main(String[] args) throws IOException {

        // Set up the socket, in and out variables

        Socket ActionClientSocket = null; //This class implements client sockets (also called just"sockets"). A socket is an endpoint for communication between two machines. 
        PrintWriter out = null; //Prints formatted representations of objects to a text-output stream
        BufferedReader in = null; //Reads text from a character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines. 
        int ActionSocketNumber = 4545;
        String ActionServerName = "localhost";
        String ActionClientID = "ClientA";

        try {
            ActionClientSocket = new Socket(ActionServerName, ActionSocketNumber);
            out = new PrintWriter(ActionClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(ActionClientSocket.getInputStream()));
        } catch (UnknownHostException e) { //Thrown to indicate that the IP address of a host could not be determined.
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "+ ActionSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + ActionClientID + " client and IO connections");
        fromServer = in.readLine();
        System.out.println(ActionClientID + " received " + fromServer + " from ActionServer");

        // This is modified as it's the client that speaks first

        while (true) {
            
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(ActionClientID + " sending " + fromUser + " to ActionServer");
                out.println(fromUser);
            }
            fromServer = in.readLine();
            System.out.println(ActionClientID + " received " + fromServer + " from ActionServer");

        }
    }
}
