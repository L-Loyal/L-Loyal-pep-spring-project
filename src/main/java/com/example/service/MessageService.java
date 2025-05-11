package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.BadMessageException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        if (accountRepository.findById(message.getPostedBy()).isPresent()) {

            if (message.getMessageText().isBlank()) {

                throw new BadMessageException("Message cannot be blank.");
            }
            else if (message.getMessageText().length() > 255) {

                throw new BadMessageException("Message cannot exceed 255 characters.");
            }   
            
            return messageRepository.save(message);
        }
        else {

            throw new BadMessageException("Message's user Id not recognized.");
        }
    }

    public List<Message> getAllMessages () {

        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById (int messageId) {

        return messageRepository.findById(messageId);
    }

    public Integer deleteMessageById (int messageId) {

        if (messageRepository.existsById(messageId)) {

            messageRepository.deleteById(messageId);

            return 1;
        } 
        else {

            return 0;
        }
    }

    public Integer updateMessageById (int messageId, Map<String, String> newText) {

        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent()) {

            if (newText.get("messageText").isBlank()) {

                throw new BadMessageException("Message cannot be blank.");
            }
            else if (newText.get("messageText").length() > 255) {

                throw new BadMessageException("Message cannot exceed 255 characters.");
            }
            else {

                Message message = optionalMessage.get();

                message.setMessageText(newText.get("messageText"));

                messageRepository.save(message);

                return 1;
            }
        }
        else {

            throw new BadMessageException("Message was not found by this ID.");
        }
    }

    public List<Message> getAllMessagesByUser (int userId) {

        return messageRepository.findAllByPostedBy(userId);
    }
}
