package me.txt.service;

import me.txt.model.Message;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;

@Component
public class FileMessageSender implements MessageSender {

    @Override
    public void send(Message message) throws Exception {
        OutputStream fio = new FileOutputStream("sensor.txt", true);
        String sensor = message + "\n";
        fio.write(sensor.getBytes());
    }

}
