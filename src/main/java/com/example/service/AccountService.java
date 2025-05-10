package com.example.service;

import java.util.Optional;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateAccountException;
import com.example.exception.RegistrationException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService (AccountRepository accountRepository) {

        this.accountRepository = accountRepository;
    }

    public Account registerAccount (Account account) throws DuplicateAccountException, RegistrationException {

        if (account.getUsername().length() >= 4) {

            if (!accountRepository.findByUsername(account.getUsername()).isPresent()) {
                
                return accountRepository.save(account);
            }
            else {

                throw new DuplicateAccountException(account.getUsername() + " is already in use.");
            }
        }
        else {

            throw new RegistrationException("Username is too short");
        }
    }

    public Account loginAccount (Account account) throws AuthenticationException {

        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());

        if (optionalAccount.isPresent()) {

            return optionalAccount.get();
        }
        else {

            throw new AuthenticationException("Username and/or password in.");
        }
    }
}
