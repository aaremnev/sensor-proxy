package me.txt.service;

import me.txt.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component
public class MessageProxy implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(MessageProxy.class);

    private final MessageQueue messageQueue;
    private final MessageSender messageSender;

    public MessageProxy(MessageQueue messageQueue, FileMessageSender messageSender) {
        this.messageQueue = messageQueue;
        this.messageSender = messageSender;

        Executors.newSingleThreadExecutor().submit(this);
    }

    @Override
    @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
    public void run() {
        while (true) {
            Message message = messageQueue.take();

            try {
                messageSender.send(message);
                log.info("Message sent: {}", message);
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

