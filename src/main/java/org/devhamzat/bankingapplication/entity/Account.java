package org.devhamzat.bankingapplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_sequence")
    @SequenceGenerator(name = "account_id_sequence", sequenceName = "account_id_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long Id;
    @Column(name = "balance")
    private double balance;
    @Column(name = "account-firstName")
    private String accountFirstName;
    @Column(name = "account-LastName")
    private String accountLastName;
    @Column(name = "account-SecondName")
    private String accountSecondName;
    @Column(name = "account-number")
    private String accountNumber;
    @Column(name = "sortCode")
    private String sortCode;
    @Column(name = "email")
    @Email()
    private String email;
    @OneToMany
    private List<Transaction> transactionList;
}
