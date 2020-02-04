package com.example.sweater;

import com.example.sweater.domain.Message;
import com.example.sweater.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessagesRepository messagesRepository;

    @GetMapping
    public String messages(Map<String, Object> model) {
        Iterable<Message> messages = messagesRepository.findAll();
        model.put("messages", messages);

        return "main";
    }

    @PostMapping
    public String messages(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);
        messagesRepository.save(message);

        Iterable<Message> messages = messagesRepository.findAll();
        model.put("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> messages = filter.isEmpty()
                ? messagesRepository.findAll()
                : messagesRepository.findByTag(filter);

        model.put("messages", messages);

        return "main";
    }
}
