package me.txt.resource;

import me.txt.model.Message;
import me.txt.service.MessageQueue;
import me.txt.service.MessageReceiver;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sensor")
public class SensorResource implements MessageReceiver {

    private final MessageQueue messageQueue;

    public SensorResource(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @GetMapping
    void addSensorData(@RequestParam("data") String data, HttpServletRequest request) {
        Message message = new Message(request.getRemoteAddr(), System.nanoTime(), data);
        receive(message);
    }

    @Override
    public void receive(Message message) {
        messageQueue.put(message);
    }

}
