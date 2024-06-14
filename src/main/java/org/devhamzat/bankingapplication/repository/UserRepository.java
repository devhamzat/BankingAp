package org.devhamzat.bankingapplication.repository;

import org.devhamzat.bankingapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    Boolean existsByEmail(String email);
}
