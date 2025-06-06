package com.example.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private AccountService accountService;

    private MessageService messageService;

    @Autowired
    public SocialMediaController (AccountService accountService, MessageService messageService) {

        this.accountService = accountService;

        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> registerHandler (@RequestBody Account account) {

        Account registeredUser = accountService.registerAccount(account);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("login")
    public ResponseEntity<Account> loginHandler (@RequestBody Account account) throws AuthenticationException {

        Account loggedUser = accountService.loginAccount(account);

        return ResponseEntity.ok(loggedUser);
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessageHandler (@RequestBody Message message) {
        
        Message createdMessage = messageService.createMessage(message);

        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessagesHandler () {

        List<Message> messages = messageService.getAllMessages();

        return ResponseEntity.ok(messages);
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageByIdHandler (@PathVariable int messageId) {

        Optional<Message> retrievedMessage = messageService.getMessageById(messageId);

        if (retrievedMessage.isPresent()) {

            return ResponseEntity.ok(retrievedMessage.get());
        }
        else {

            return ResponseEntity.ok(null);
        }
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByIdHandler (@PathVariable int messageId) {

        Integer rowUpdated = messageService.deleteMessageById(messageId);

        if (rowUpdated == 1) {

            return ResponseEntity.ok(rowUpdated);
        }
        else {

            return ResponseEntity.ok(null);
        }
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessageByIdHandler (@PathVariable int messageId, @RequestBody Map<String, String> message) {

        Integer rowUpdated = messageService.updateMessageById(messageId, message);

        return ResponseEntity.ok(rowUpdated);
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUserIdHandler (@PathVariable int accountId) {

        List<Message> messages = messageService.getAllMessagesByUser(accountId);

        return ResponseEntity.ok(messages);
    }
}
