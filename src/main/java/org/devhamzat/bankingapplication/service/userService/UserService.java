package org.devhamzat.bankingapplication.service.userService;

import org.devhamzat.bankingapplication.dto.Response;
import org.devhamzat.bankingapplication.dto.UserRequest;

public interface UserService {
    Response createAccount(UserRequest userRequest);
}
