package persistence;

import org.json.JSONObject;
//Note: I replicate this code from "JsonSerializationDemo" that is provided.

public interface Writeable {
    JSONObject toJson();
}
