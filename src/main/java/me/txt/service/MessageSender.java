package me.txt.service;

import me.txt.model.Message;

public interface MessageSender {

    void send(Message message) throws Exception;

}
