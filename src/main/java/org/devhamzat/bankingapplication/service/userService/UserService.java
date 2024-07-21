package org.devhamzat.bankingapplication.service.userService;

import org.devhamzat.bankingapplication.dto.*;

public interface UserService {
    Response createAccount(UserRequest userRequest);

    Response balanceEnquiry(EnquiryRequest enquiryRequest);

    String nameEnquiry(EnquiryRequest enquiryRequest);

    Response creditAccount(CreditANDDebitRequest creditANDDebitRequest);

    Response DebitAccount(CreditANDDebitRequest creditANDDebitRequest);

    Response transfer(TransferRequest transferRequest);
}
