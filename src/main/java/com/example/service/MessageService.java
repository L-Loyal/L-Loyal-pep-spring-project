package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.BadMessageException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    private AccountRepository accountRepository;

    @Autowired
    public MessageService (MessageRepository messageRepository, AccountRepository accountRepository) {

        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage (Message message) {

        if (accountRepository.findById(message.getPostedBy()).isPresent() 
            && !message.getMessageText().isBlank() 
            && message.getMessageText().length() <= 255) {
            
            return messageRepository.save(message);
        }
        else {

            throw new BadMessageException("Message may be too long, blank, or not posted by a registered user.");
        }
    }

    public List<Message> getAllMessages () {

        return messageRepository.findAll();
    }
}
