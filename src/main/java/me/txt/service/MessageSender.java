package me.txt.service;

import me.txt.model.Message;

/**
 * An abstraction of message sender.
 * Can throw any exception to cancel and retry sending message later.
 */
public interface MessageSender {

    void send(Message message) throws Exception;

}
