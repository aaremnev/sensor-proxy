package me.txt.service;

import me.txt.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * MessageQueue service is responsible for accumulating sensor data,
 * feed MessageProxy when new messages are available.
 */
@Component
public class MessageQueue {

    private final static Logger log = LoggerFactory.getLogger(MessageQueue.class);

    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();

    public void put(String clientId, String data) {
        Message message = new Message(clientId, System.nanoTime(), data);
        queue.add(message);
        log.info("Message received: {}", message);
    }

    /**
     * Take new message or await till it is available.
     * @return next message object
     */
    public Message take() throws InterruptedException {
        return queue.take();
    }

}
