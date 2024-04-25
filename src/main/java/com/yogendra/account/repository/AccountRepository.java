package com.yogendra.account.repository;

import com.yogendra.domain.Account;
import com.yogendra.util.Amount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findByAccountNumber(String accountNumber);

    @Modifying
    @Query("update Account a set a.balance = ?1 where a.accountNumber = ?2")
    int updateBalanceByAccountNumber(Amount balance, String accountNumber);

    int deleteByAccountNumber(String accountHolder);
}
