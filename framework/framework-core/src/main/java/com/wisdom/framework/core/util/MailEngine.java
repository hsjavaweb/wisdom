package com.wisdom.framework.core.util;


import com.wisdom.framework.core.context.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * Class for sending e-mail messages based on Velocity templates
 * or with attachments.
 *
 * @author Matt Raible
 */
public class MailEngine {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private String defaultFrom;


    public synchronized MailSender getMailSender() {
        JavaMailSenderImpl  mailSender= SpringContextUtil.getBean("mailSender");
        log.info("use mailSender:{}",mailSender.getUsername());
        return mailSender;
    }


    public void setFrom(String from) {
        this.defaultFrom = from;
    }

    /**
     * Send a simple message based on a Velocity template.
     *
     * @param title          the message to populate
     * @param to          the message to populate
     * @param text the Velocity template to use (relative to classpath)
     */
    public void sendMessage(String title,String to, String text) {
        SimpleMailMessage msg=new SimpleMailMessage();
        msg.setFrom(defaultFrom);
        msg.setSubject(title);
        msg.setTo(to.split(","));
        msg.setSentDate(new Date());
        msg.setText(text);
        send(msg);
    }

    /**
     * Send a simple message with pre-populated values.
     *
     * @param msg the message to send
     * @throws MailException
     *          when SMTP server is down
     */
    public void send(SimpleMailMessage msg) throws MailException {
        try {
            if(!"true".equalsIgnoreCase(SpringContextUtil.getSystemProperties("mail.open"))){
                log.info("邮箱开关为关，不发送邮件");
                return;
            }
            String md5String = SecureUtil.MD5String(msg.getText(), "utf-8");
            JavaMailSenderImpl mailSender = (JavaMailSenderImpl) getMailSender();
            msg.setFrom(mailSender.getUsername());
            mailSender.send(msg);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Convenience method for sending messages with attachments.
     *
     * @param recipients     array of e-mail addresses
     * @param sender         e-mail address of sender
     * @param resource       attachment from classpath
     * @param bodyText       text in e-mail
     * @param subject        subject of e-mail
     * @param attachmentName name for attachment
     * @throws MessagingException thrown when can't communicate with SMTP server
     */
    public void sendMessage(String[] recipients, String sender,
                            ClassPathResource resource, String bodyText,
                            String subject, String attachmentName)
            throws MessagingException {
        MimeMessage message = ((JavaMailSenderImpl) getMailSender()).createMimeMessage();

        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(recipients);

        // use the default sending if no sender specified
        if (sender == null) {
            helper.setFrom(defaultFrom);
        } else {
            helper.setFrom(sender);
        }

        helper.setText(bodyText);
        helper.setSubject(subject);

        helper.addAttachment(attachmentName, resource);

        ((JavaMailSenderImpl) getMailSender()).send(message);
    }
}
