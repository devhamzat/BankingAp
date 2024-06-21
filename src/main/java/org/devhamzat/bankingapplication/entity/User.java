package org.devhamzat.bankingapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.devhamzat.bankingapplication.utils.enums.Gender;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String stateOfOrigin;
    private String accountNumber;
    private Gender gender;
    private String address;
    private BigDecimal accountBalance;
    private String phoneNumber;
    private String altPhoneNumber;
    private String altEmail;
    private String status;
    private LocalDate dob;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

}
