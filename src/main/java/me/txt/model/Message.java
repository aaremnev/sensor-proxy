package me.txt.model;

/**
 * Represents sensor data with additional properties.
 */
public class Message {

    private final String clientId;
    private final long time;
    private final String data;

    public Message(String clientId, long time, String data) {
        this.clientId = clientId;
        this.time = time;
        this.data = data;
    }

    public Message(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot create message from null string");
        }

        String[] tokens = value.split("\\|", 3);

        this.clientId = tokens[0];
        this.time = Long.parseLong(tokens[1]);
        this.data = tokens[2];
    }

    public String toString() {
        return clientId + "|" + time + "|" + data;
    }

}
