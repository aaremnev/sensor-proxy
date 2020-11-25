package me.txt.service;

import me.txt.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class MessageQueue {

    private final static Logger log = LoggerFactory.getLogger(MessageQueue.class);

    private final BlockingDeque<Message> queue = new LinkedBlockingDeque<>();

    public void put(String clientId, String data) {
        Message message = new Message(clientId, System.nanoTime(), data);
        queue.add(message);
        log.info("Message received: {}", message);
    }

    public Message take() {
        try {
            return queue.takeFirst();
        }
        catch (InterruptedException e) {
            // should not happen
            return null;
        }
    }

    public void putBack(Message message) {
        try {
            queue.putFirst(message);
            log.info("Message returned: {}", message);
        }
        catch (InterruptedException e) {
            // should not happen
        }
    }

}
