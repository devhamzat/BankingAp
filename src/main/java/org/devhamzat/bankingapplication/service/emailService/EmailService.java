package org.devhamzat.bankingapplication.service.emailService;

import org.devhamzat.bankingapplication.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
}
