package com.yogendra.domain;

import com.yogendra.util.Amount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
    @SequenceGenerator(name = "account_id_seq", allocationSize = 1)
    private Integer id;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_holder")
    private String accountHolder;
    @AttributeOverride(name = "value", column = @Column(name = "balance"))
    private Amount balance;
    @Column(name = "opening_date", insertable = false)
    private LocalDate openingDate;
    @Column(name = "email")
    private String email;


    public Account(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = Amount.valueOf("0.00");
    }

    public Account(String accountNumber, String accountHolder, String balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = Amount.valueOf(balance);
    }


}
