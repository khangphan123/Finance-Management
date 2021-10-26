package persistence;

import model.Customer;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
//Note: I replicate this code from "JsonSerializationDemo" that is provided.
// This class is writes Customer from JSON stored in file

public class JsonWriter {
    private static int CHOICES = 6;
    private PrintWriter writer;
    private String destination;

    //EFFECTS: The constructor is used to writes from the source file

    public JsonWriter(String destination) {
        this.destination = destination;
    }
    // MODIFIES: this
    // EFFECTS: opens writer
    // throws FileNotFoundException if no file is found.

    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(Customer cst) {
        JSONObject json = cst.toJson();
        saveToFile(json.toString(CHOICES));
    }
    // MODIFIES: this
    // EFFECTS: closes writer

    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
