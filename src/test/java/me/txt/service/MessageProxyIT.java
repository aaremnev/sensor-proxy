package me.txt.service;

import me.txt.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class MessageProxyIT {

    @Mock private MessageSender messageSender;
    private MessageQueue messageQueue;

    @BeforeEach
    void setUp() {
        openMocks(this);

        messageQueue = new MessageQueue();

        // start proxy
        new MessageProxy(messageQueue, messageSender);
    }

    private Message createMessage(String clientId, String data) {
        return new Message(clientId, System.nanoTime(), data);
    }

    @Test
    void sendWithRetryRetriesOnSuccessfulSend() throws Exception {
        Message message1 = createMessage("client1", "success");
        Message message2 = createMessage("client2", "success");

        messageQueue.put(message1);
        messageQueue.put(message2);

        verify(messageSender, timeout(100)).send(message1);
        verify(messageSender, timeout(100)).send(message2);
    }

    @Test
    void sendWithRetryRetriesOnException() throws Exception {
        Message message = createMessage("client2", "error");

        doThrow(new RuntimeException("Unexpected")).when(messageSender).send(message);

        messageQueue.put(message);

        // 2 attempts after 2 seconds
        verify(messageSender, timeout(2100).times(2)).send(message);
    }

}