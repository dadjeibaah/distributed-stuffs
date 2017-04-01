package com.onecreation;

import com.google.gson.Gson;
import com.onecreation.common.Datasource;
import com.onecreation.common.StudentRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class StudentRecordComputeThread extends Thread {
    private BlockingQueue<StudentRecord> sharedDataStructure;
    private InputStream incomingRecord;
    private OutputStream acknowledgeStream;
    private final Gson JSONMarshaller = new Gson();



    public StudentRecordComputeThread(BlockingQueue<StudentRecord> sharedDataStructure, InputStream incomingRecord, OutputStream acknowledgeStream) {
        this.sharedDataStructure = sharedDataStructure;
        this.incomingRecord = incomingRecord;
        this.acknowledgeStream = acknowledgeStream;

    }

    @Override
    public void run() {
        ObjectInputStream reader = null;
        try{
             reader = new ObjectInputStream(incomingRecord);
        }catch (IOException e){
            e.printStackTrace(System.out);
        }

        PrintWriter ackWriter = new PrintWriter(acknowledgeStream, true);
        while (true) {
            StudentRecord student = null;
            try {
                student = JSONMarshaller.fromJson((String)reader.readObject(), StudentRecord.class);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(System.out);
                break;
            }

            if (student != null){

                try {
                    if (student.getId().equals("0")) {
                        sharedDataStructure.put(student);
                        ackWriter.close();
                        break;
                    }

                    student.computeAge();
                    sharedDataStructure.put(student);

                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                }
                System.out.println("Processed record for...");
                System.out.println(student);

            }



        }

    }
}

