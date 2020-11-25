package me.txt.service;

import me.txt.model.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Component
public class MessageQueue {

    private final BlockingDeque<Message> queue = new LinkedBlockingDeque<>();

    public void put(String clientId, String data) {
        Message message = new Message(clientId, System.nanoTime(), data);
        queue.add(message);
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
        } catch (InterruptedException e) {
            // should not happen
        }
    }

}
