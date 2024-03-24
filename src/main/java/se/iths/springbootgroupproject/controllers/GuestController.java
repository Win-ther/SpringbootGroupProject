package se.iths.springbootgroupproject.controllers;

import org.springframework.web.bind.annotation.*;
import se.iths.springbootgroupproject.dto.PublicMessageAndUsername;
import se.iths.springbootgroupproject.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class GuestController {

    private final MessageService messageService;

    public GuestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("messages")
    List<PublicMessageAndUsername> all(){
            return messageService.findAllByPrivateMessageIsFalse();
    }

}