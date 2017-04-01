package com.onecreation;

import com.onecreation.common.ClientArgumentParser;
import com.onecreation.common.ClientCommunication;
import com.onecreation.common.ServerCommunication;
import com.onecreation.common.StudentRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.concurrent.*;

/**
 * Created by dennis on 11/3/16.
 */
public class Main {
    private static final String HOST_NAME = "localhost";
    private static final String PORT_NUMBER = "8002";
    private static final String HOST_NAME_KEY = "hostName";
    private static final String PORT_NUMBER_KEY = "portNumber";
    private static final String SERVER_PORT_NUMBER_KEY = "serverPort";
    private static final String SERVER_PORT = "8001";

    public static void main(String[] args) {
        InputStream in;
        ObjectOutputStream out = null;

        Hashtable<String, String> finalArgs = new ClientArgumentParser(new Hashtable<String, String>() {{
            put(HOST_NAME_KEY, HOST_NAME);
            put(PORT_NUMBER_KEY, PORT_NUMBER);
            put(SERVER_PORT_NUMBER_KEY, SERVER_PORT);
        }}).parseCommandLineArgs(args);
        ServerCommunication serverComm = new ServerCommunication(finalArgs.get(SERVER_PORT_NUMBER_KEY));

        ExecutorService threadService = Executors.newFixedThreadPool(5);
        BlockingQueue<StudentRecord> queue = new LinkedBlockingQueue<>();
        while (true) {
            in = serverComm.getInputStream();
            ClientCommunication clientComm = new ClientCommunication(finalArgs.get(HOST_NAME_KEY), finalArgs.get(PORT_NUMBER_KEY));
            threadService.execute(new StudentRecordComputeThread(queue,in, serverComm.getOutputStream()));
            threadService.execute(new StudentRecordWriteThread(queue, clientComm.getOutputStream(), clientComm.getInputStream()));
        }

    }
}

