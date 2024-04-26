package com.yogendra.account.service;


import com.yogendra.account.repository.AccountRepository;
import com.yogendra.domain.Account;
import com.yogendra.util.Email;
import com.yogendra.util.Amount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    Logger logger = LoggerFactory.getLogger("com.yogendra.account.service.AccountService");

    private final AccountRepository accountRepository;
    private final JmsTemplate jmsTemplate;


    public AccountService(AccountRepository accountRepository, JmsTemplate jmsTemplate) {
        this.accountRepository = accountRepository;
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    public Account save(Account account) {
        return accountRepository.save(account);
    }


    @Transactional
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Transactional
    public void transferAmount(String senderAccountNumber, String receiverAccountNumber, String amount) {
        Account senderAccount = accountRepository.findByAccountNumber(senderAccountNumber);
        Amount senderUpdatedAmount = senderAccount.getBalance().subtract(Amount.valueOf(amount));
        accountRepository.updateBalanceByAccountNumber(senderUpdatedAmount, senderAccountNumber);
        jmsTemplate
                .convertAndSend("email",
                        new Email(senderAccount.getEmail(),
                                String.format("""
                                        %s debited from account number %s
                                        """, senderUpdatedAmount.toString(), senderAccountNumber)
                        )
                );

        Account receiverAccount = accountRepository.findByAccountNumber(receiverAccountNumber);
        Amount receiverUpdatedAmount = receiverAccount.getBalance().add(Amount.valueOf(amount));
        accountRepository.updateBalanceByAccountNumber(receiverUpdatedAmount, receiverAccountNumber);
        jmsTemplate
                .convertAndSend("email",
                        new Email(receiverAccount.getEmail(),
                                String.format("""
                                        %s credited to account number %s
                                        """, receiverUpdatedAmount.toString(), receiverAccountNumber)
                        )
                );

    }
}
