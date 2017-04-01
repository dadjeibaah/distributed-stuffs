package com.onecreation;

import com.onecreation.common.ClientArgumentParser;
import com.onecreation.common.ClientCommunication;

import java.util.Hashtable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dennis on 9/23/16.
 */
public class Main {

    private static final String HOST_NAME = "cs4727.etsu.edu";
    private static final String FILE_NAME = "students.txt";
    private static final String PORT_NUMBER = "8001";
    private static final String INPUT_KEY = "fileName";
    private static final String HOST_NAME_KEY = "hostName";
    private static final String PORT_NUMBER_KEY = "portNumber";

    public static void main(String[] args) {
        ClientCommunication clientComm;
        Hashtable<String, String> finalArgs = new ClientArgumentParser(new Hashtable<String, String>()
        {{
            put(INPUT_KEY, FILE_NAME);
            put(HOST_NAME_KEY, HOST_NAME);
            put(PORT_NUMBER_KEY, PORT_NUMBER);
        }}).parseCommandLineArgs(args);
        ExecutorService clientThreadService = Executors.newFixedThreadPool(5);

        clientComm = new ClientCommunication(finalArgs.get(HOST_NAME_KEY),finalArgs.get(PORT_NUMBER_KEY));
        clientThreadService.execute(new StudentRecordInputThread(clientComm.getOutputStream(), clientComm.getInputStream(), finalArgs.get(INPUT_KEY)));
        clientThreadService.shutdown();
    }
}
