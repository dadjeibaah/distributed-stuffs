package com.onecreation;

import com.onecreation.common.StudentRecord;

import java.io.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by dennis on 11/8/16.
 */
public class StudentRecordWriteThread extends Thread {
    private BlockingQueue<StudentRecord> sharedDataStructure;
    private OutputStream out;
    private InputStream in;


    public StudentRecordWriteThread(BlockingQueue<StudentRecord> sharedDataStructure, OutputStream outputStream, InputStream inputStream){
        this.sharedDataStructure = sharedDataStructure;
        this.out = outputStream;
        this.in = inputStream;

    }

    public void run(){
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(out);
        }catch (IOException e){
            e.printStackTrace(System.out);
            System.exit(1);
        }
        while(true){
            try{
                StudentRecord record = sharedDataStructure.take();
                if(record.getId().equals("0")){
                    objectOutputStream.writeObject(record);
                    objectOutputStream.reset();
                    break;
                }
                objectOutputStream.writeObject(record);
            }
            catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
