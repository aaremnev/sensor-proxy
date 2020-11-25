package me.txt.resource;

import me.txt.service.MessageQueue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sensor")
public class SensorResource {

    private final MessageQueue messageQueue;

    public SensorResource(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    @GetMapping
    void addSensorData(@RequestParam("data") String data, HttpServletRequest request) {
        messageQueue.put(request.getRemoteAddr(), data);
    }

}
