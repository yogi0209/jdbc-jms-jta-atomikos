package com.yogendra.account.web;

import com.yogendra.account.service.AccountService;
import com.yogendra.domain.Account;
import com.yogendra.util.Amount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> addAccount(@RequestBody Account account){
        account.setBalance(Amount.valueOf("0.00"));
        Account newAccount = accountService.save(account);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(newAccount.getAccountNumber())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/transfer/sender/{senderAccount}/receiver/{receiverAccount}/amount/{amount}")
    public ResponseEntity<Void> transferBalance(
            @PathVariable String senderAccount, @PathVariable String receiverAccount, @PathVariable String amount){
        accountService.transferAmount(senderAccount, receiverAccount, amount);
        return ResponseEntity.ok().build();
    }


}
