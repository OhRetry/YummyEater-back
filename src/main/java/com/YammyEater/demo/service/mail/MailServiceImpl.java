package com.YammyEater.demo.service.mail;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.exception.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender emailSender;


    public void sendEmail(String sendTo, String title, String content) {
        try {
            final MimeMessagePreparator preparator = mimeMessage -> {
                final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                helper.setTo(sendTo);
                helper.setSubject(title);
                helper.setText(content);
            };
            emailSender.send(preparator);
        }
        catch (MailSendException e) {
            return;
        }
        catch (MailException e) {
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
