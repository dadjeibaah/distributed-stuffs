package com.onecreation.common;

import java.io.*;
import java.net.*;



/**
 * Created by dennis on 9/23/16.
 */
public class ClientCommunication {
    private Socket clientSocket;
    public ClientCommunication(String hostname, String portNumber){
        try {
            int parsedPortNumber = Integer.parseInt(portNumber);
            clientSocket = new Socket(hostname, parsedPortNumber);
        }catch (IOException e){
            System.out.println("an error occured with the output stream");
            e.printStackTrace(System.out);
        }
        catch (NumberFormatException e){
            System.out.println("portNumber is invalid" + portNumber);
            e.printStackTrace(System.out);
            System.exit(0);
        }
    }
    public OutputStream getOutputStream(){
        OutputStream out = null;
        try{
            out = clientSocket.getOutputStream();
        }catch (IOException e){
            e.printStackTrace(System.out);
        }
        return out;

    }

    public InputStream getInputStream() {
        InputStream in = null;
        try{
            in = clientSocket.getInputStream();
        }catch (IOException e){
            e.printStackTrace(System.out);
        }
        return in;
    }
}
