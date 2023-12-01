package com.YammyEater.demo.service.mail;

import com.YammyEater.demo.constant.error.ErrorCode;
import com.YammyEater.demo.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;
    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Async
    public void sendEmail(String sendTo, String title, String content) {
        try {
            final MimeMessagePreparator preparator = mimeMessage -> {
                final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                helper.setTo(sendTo);
                helper.setSubject(title);
                helper.setText(content, true);
            };
            emailSender.send(preparator);
        }
        catch (MailSendException e) {
            logger.info("이메일 전송 실패", e);
            return;
        }
        catch (MailException e) {
            logger.error("이메일 서비스 에러", e);
            throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
