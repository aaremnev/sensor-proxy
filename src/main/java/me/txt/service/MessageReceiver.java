package me.txt.service;

import me.txt.model.Message;

/**
 * An abstraction of message receiver.
 */
public interface MessageReceiver {

    void receive(Message message);

}
