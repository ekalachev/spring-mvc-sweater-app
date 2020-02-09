package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repositories.MessagesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/messages")
public class MessagesController {
    @Value("${upload.path}")
    private String uploadPath;

    private final MessagesRepository messagesRepository;

    public MessagesController(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }

    @GetMapping
    public String messages(@RequestParam(required = false, defaultValue = "") String filter, Map model) {
        Iterable<Message> messages = filter.isEmpty()
                ? messagesRepository.findAll()
                : messagesRepository.findByTag(filter);

        model.put("messages", messages);
        model.put("filter", filter);

        return "messages";
    }

    @PostMapping
    public String messages(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam("file") MultipartFile file,
            Map<String, Object> model
    ) throws IOException {
        Message message = new Message(text, tag, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            message.setFilename(resultFileName);
        }

        messagesRepository.save(message);

        Iterable<Message> messages = messagesRepository.findAll();
        model.put("messages", messages);

        return "messages";
    }
}
