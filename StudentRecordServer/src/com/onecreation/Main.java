package com.onecreation;

import com.onecreation.common.ClientArgumentParser;
import com.onecreation.common.Datasource;
import com.onecreation.common.ServerCommunication;
import com.onecreation.common.StudentRecord;
import org.omg.CORBA.Object;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Hashtable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by dennis on 9/23/16.
 */
public class Main {
    private static final String FILE_NAME = "processed.txt";
    private static final String OUTPUT_KEY = "fileName";
    private static final String SERVER_PORT_NUMBER_KEY = "serverPort";
    private static final String SERVER_PORT = "8002";
    private static final Datasource datasource = Datasource.getInstance();

    public static void main(String[] args){
        InputStream in;
        Hashtable<String, String> finalArgs = new ClientArgumentParser(new Hashtable<String, String>() {{
            put(OUTPUT_KEY, FILE_NAME);
            put(SERVER_PORT_NUMBER_KEY, SERVER_PORT);
        }}).parseCommandLineArgs(args);
        ServerCommunication serverComm = new ServerCommunication(finalArgs.get(SERVER_PORT_NUMBER_KEY));
        ExecutorService threadService = Executors.newFixedThreadPool(5);
        while(true){
            in = serverComm.getInputStream();
            threadService.execute(new StudentRecordOutputThread(finalArgs.get(OUTPUT_KEY), in, datasource));

        }
    }
}
