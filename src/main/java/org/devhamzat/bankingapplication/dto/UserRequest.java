package org.devhamzat.bankingapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.devhamzat.bankingapplication.utils.enums.Gender;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String stateOfOrigin;
    private Gender gender;
    private String address;
    private String phoneNumber;
    private String altPhoneNumber;
    private String altEmail;
}
