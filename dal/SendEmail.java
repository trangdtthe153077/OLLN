/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Laptop88
 */
public class SendEmail {

    private final String userEmail;
    private final String userHash;

    public SendEmail(String userEmail, String userHash) {
        this.userEmail = userEmail;
        this.userHash = userHash;
    }

    public void sendConfirmEMail(String toemail, String coursename, String content) {
        // Enter the email address and password for the account from which verification link will be send
        String email = "elearning2401@gmail.com";
        String password = "kgfcbcprfqsbtgdw";

        Properties theProperties = new Properties();

        theProperties.put("mail.smtp.auth", "true");
        theProperties.put("mail.smtp.starttls.enable", "true");
        theProperties.put("mail.smtp.host", "smtp.gmail.com");
        theProperties.put("mail.smtp.port", "587");
        theProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getInstance(theProperties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toemail));
            message.setSubject("eLEARNING - Your registration of " + coursename);

            message.setContent(content, "text/html");

            Transport.send(message);

            System.out.println("Successfully sent Verification Link");

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void sendMail() {
        // Enter the email address and password for the account from which verification link will be send
        String email = "elearning2401@gmail.com";
        String password = "kgfcbcprfqsbtgdw";

        Properties theProperties = new Properties();

        theProperties.put("mail.smtp.auth", "true");
        theProperties.put("mail.smtp.starttls.enable", "true");
        theProperties.put("mail.smtp.host", "smtp.gmail.com");
        theProperties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(theProperties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Email Verification Link");
            message.setText("Click this link to confirm your email address and complete setup for your account."
                    + "\n\nVerification Link: " + "http://localhost:8084/Online-Learning-SWP/active?key1=" + userEmail + "&key2=" + userHash);

            Transport.send(message);

            System.out.println("Successfully sent Verification Link");

        } catch (MessagingException e) {

        }

    }
}
