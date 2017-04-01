package com.onecreation;

import com.onecreation.common.StudentRecord;
import com.google.gson.Gson;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

/**
 * Created by dennis on 9/2/16.
 */
public class StudentRecordInputThread extends Thread {
    private final Gson JSONMarshaller = new Gson();
    private OutputStream out;
    private InputStream in;
    private String lastName = "";
    private String firstName = "";
    private String id = "";
    private String dob = "";
    private String inputFile;


    public StudentRecordInputThread(OutputStream output, InputStream input, String inputFile) {
        this.out = output;
        this.in = input;
        this.inputFile = inputFile;
    }

    @Override
    public void run() {
        super.run();
        try (Scanner reader = new Scanner(new File(inputFile))) {
            ObjectOutputStream writer = new ObjectOutputStream(out);
            while (reader.hasNextLine()) {
                lastName = reader.nextLine();
                firstName = reader.nextLine();
                id = reader.nextLine();
                dob = reader.nextLine();

                StudentRecord record = new StudentRecord
                        .StudentRecordBuilder(firstName, lastName)
                        .id(id)
                        .dateOfBirth(dob).build();
                System.out.println(String.format("Reading student record: %s", record.toString()));
                System.out.println(JSONMarshaller.toJson(record));
                writer.writeObject(JSONMarshaller.toJson(record));
            }

            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace(System.out);
        }
    }
}
