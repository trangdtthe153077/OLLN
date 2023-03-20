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
public class SendRandomPasstoEmail {

    private final String userEmail;
    private final String userPass;
    private final String userName;
    public SendRandomPasstoEmail(String userEmail, String userPass, String userName) {
        this.userEmail = userEmail;
        this.userPass = userPass;
        this.userName = userName;
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
            message.setContent("<div>\n"
                    + "<td style=\"padding:20px;border-bottom:2px solid #dddddd\" bgcolor=\"#ffffff\">\n"
                    + "    <p>Xin chào <strong>"+ userName +"</strong>,</p>\n"
                    + "<p>Cảm ơn bạn đã đăng ký tài khoản trên eLEARNING.</p>\n"
                    + "<p>Đây là mật khẩu tài khoản của bạn:</p>\n"
                    + "<div  style=\"text-align:center;\">\n"
                    + "    <h1 style=\"color:#fff;text-decoration:none;display:inline-block;background-color:#17A2B8;padding:12px 20px;font-weight:bold;border-radius:4px\">"+ userPass +"</h1>\n"
                    + "    </div>\n"
                    + "<p>Liên hệ với chúng tôi để được hỗ trợ nhiều hơn: <br>\n"
                    + "Hotline: <strong>(083) 9911 131</strong> <br>\n"
                    + "Email: <strong><a href=\"mailto:hotro@topcv.vn\" target=\"_blank\">elearning2401@gmail.com</a></strong> <br>\n"
                    + "</p><p><strong>Trân trọng, <br>\n"
                    + "eLEARNING</strong></p>\n"
                    + "    <span style=\"font-size:11px;font-style:italic;text-align:center;display:block;color:#888\">-- Đây là email tự động. Xin bạn vui lòng không gửi phản hồi vào hộp thư này --</span>\n"
                    + "<p></p></td>\n"
                    + "</div>", "text/html; charset=UTF-8");

            Transport.send(message);

            System.out.println("Successfully sent Verification Link");

        } catch (MessagingException e) {

        }

    }
}
