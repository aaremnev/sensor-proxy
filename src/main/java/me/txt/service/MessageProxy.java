package me.txt.service;

import me.txt.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component
@SuppressWarnings("InfiniteLoopStatement")
public class MessageProxy {

    private final static Logger log = LoggerFactory.getLogger(MessageProxy.class);

    private final MessageQueue messageQueue;
    private final MessageSender messageSender;

    public MessageProxy(MessageQueue messageQueue, FileMessageSender messageSender) {
        this.messageQueue = messageQueue;
        this.messageSender = messageSender;

        Executors.newSingleThreadExecutor().submit(this::runProxy);
    }

    private void runProxy() {

        // process messages
        while (true) {
            try {
                // get message, wait if needed
                Message message = messageQueue.take();

                // try to send message
                sendWithRetry(message);

                // sent successfully
                log.info("Message sent: {}", message);
            }
            catch (InterruptedException e) {
                // ignore
            }
        }
    }

    @SuppressWarnings("BusyWait")
    private void sendWithRetry(Message message) {

        while (true) {
            try {
                // try to send message
                messageSender.send(message);

                break;
            }
            catch (Exception e) {
                log.error("Failed to send message: {}", message);

                // wait before next attempt when sender will be available
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException interruptedException) {
                    // ignore
                }
            }
        }

    }

}

