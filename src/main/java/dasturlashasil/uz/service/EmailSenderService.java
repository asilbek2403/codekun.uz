package dasturlashasil.uz.service;

import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.repository.emailR.EmailHistoryRepository;
import dasturlashasil.uz.service.email.EmailHistoryService;
import dasturlashasil.uz.util.JWTUtil;
import dasturlashasil.uz.util.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailHistoryService  emailHistoryService;

    public void sendRegistrationEmail( String toAccount){

        Integer smsCode = RandomUtil.fiveDigit();//send...
//        String body= "Sms code Click http://localhost:8080/api/auth/registration/confirm/%s/%d" +smsCode;
        //v2
        String body= "Men Asilbekman  http://localhost:8080/api/auth/registration/email/verification/%s\n";
//        String codd="https:/github.com/asilbek2403/codekun.uz";
        String jwtToken=  JWTUtil.encodeForRegistration(fromAccount,smsCode);//Jwt token Key bilan
        body = String.format(body,jwtToken);

        sendSimpleMessage("Registrations Sinove --> " ,body,toAccount);
        emailHistoryService.create(body, smsCode, toAccount);

    }

    public void sendRegistrationStyledEmail(String toAccount) {
        Integer smsCode = RandomUtil.fiveDigit();
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +                "    <meta charset=\"UTF-8\">\n" +

                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1 style=\"text-align: center\">Kunuz Portaliga xush kelibsiz.</h1>\n" +
                "<br>\n" +
                "<h4>Ro'yhatdan o'tishni tugatish uchun quyidagi linkga bosing</h4>\n" +
                "<a style=\" background-color: indianred;\n" +
                "  color: black;\n" +
                "  padding: 10px 20px;\n" +
                "  text-align: center;\n" +
                "  text-decoration: none;\n" +
                "  display: inline-block;\"\n" +
                "   href=\"http://localhost:8081/api/v1/auth/registration/email/verification/%s\">Ro'yhatdan\n" +
                "    o'tishni tugatish </a>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        String jwtToken = JWTUtil.encodeForRegistration(toAccount, smsCode);
        body = String.format(body, jwtToken);
        // send
        sendMimeMessage("Registration complete", body, toAccount);
        // save to db
        emailHistoryService.create(body, smsCode, toAccount);
    }



    private String sendSimpleMessage(String subject, String body, String toAccount) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(body);
        javaMailSender.send(msg);

        return "Mail was send yaxshi OK";
    }




    //v2
    private String sendMimeMessage(String subject, String body, String toAccount) {
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailSender.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Mail was send hop OK";
    }



}
