package org.devhamzat.bankingapplication.repository;

import org.devhamzat.bankingapplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findAccountByAccountNumber(String accountNumber);

//    Optional<Account> findAccountByAccountHolderAndAccountNumber(String accountHolder, String accountNumber);


    Optional<Account> findByEmail(String email);
}
