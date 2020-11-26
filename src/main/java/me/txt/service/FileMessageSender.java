package me.txt.service;

import me.txt.model.Message;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Implements message sending by appending them to a file.
 * Throws an exception when file write fails (e.g. if user/file has no write permissions).
 */
@Component
public class FileMessageSender implements MessageSender {

    public static final String SENSOR_FILE = "sensor.txt";

    @Override
    public void send(Message message) throws Exception {
        try (OutputStream fio = new FileOutputStream(SENSOR_FILE, true)) {
            String sensor = message + "\n";
            fio.write(sensor.getBytes());
        }
    }

}
