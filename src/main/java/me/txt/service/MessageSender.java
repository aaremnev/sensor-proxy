package me.txt.service;

import me.txt.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.Executors;

@Component
public class MessageSender implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(MessageSender.class);

    private final MessageQueue messageQueue;

    public MessageSender(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;

        Executors.newSingleThreadExecutor().submit(this);
    }

    @Override
    @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
    public void run() {
        while (true) {
            Message message = messageQueue.take();
            try (OutputStream fio = new FileOutputStream("sensor.txt", true)) {
                String sensor = message + "\n";
                fio.write(sensor.getBytes());
            }
            catch (Exception e) {
                log.error("Failed to send message: {}", message);
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {
                    // interrupted
                }
                messageQueue.putBack(message);
            }
        }
    }

}

