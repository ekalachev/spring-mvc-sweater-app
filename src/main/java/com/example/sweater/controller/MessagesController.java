package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/messages")
public class MessagesController {
    @Autowired
    private MessagesRepository messagesRepository;

    @GetMapping
    public String messages(Map<String, Object> model) {
        Iterable<Message> messages = messagesRepository.findAll();
        model.put("messages", messages);

        return "messages";
    }

    @PostMapping
    public String messages(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);
        messagesRepository.save(message);

        Iterable<Message> messages = messagesRepository.findAll();
        model.put("messages", messages);

        return "messages";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> messages = filter.isEmpty()
                ? messagesRepository.findAll()
                : messagesRepository.findByTag(filter);

        model.put("messages", messages);

        return "messages";
    }
}
