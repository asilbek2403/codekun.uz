package dasturlashasil.uz.service;

import dasturlashasil.uz.Enums.ProfileRoleEnum;
import dasturlashasil.uz.repository.emailR.EmailHistoryRepository;
import dasturlashasil.uz.service.email.EmailHistoryService;
import dasturlashasil.uz.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        String body= "men asilbekman dastur kodimni tekshirdim : " +
                "Sms code Click http://localhost:8080/api/auth/registration/confirm/%s/%d/%s" ;
        String codd="https:/github.com/asilbek2403/codekun.uz";
        body = String.format(body,toAccount,smsCode,codd);

        sendSimpleMessage("Registrations Sinove" ,body,toAccount);
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


}
