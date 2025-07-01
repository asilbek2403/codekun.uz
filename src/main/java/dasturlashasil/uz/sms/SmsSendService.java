package dasturlashasil.uz.sms;

import dasturlashasil.uz.Dto.sms.SmsRequestDto;
//import dasturlashasil.uz.exceptons.AppBadException;
import dasturlashasil.uz.exceptons.AppBadException;
import dasturlashasil.uz.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class SmsSendService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsHistoryService smsHistoryService;

    public void sendRegistrationSms(String phone) {
        Integer smsCode = RandomUtil.fiveDigit();
//        String body = "<#>G'iybat.uz partali. Ro'yxatdan o'tish uchun tasdiqlash kodi (code) : " + smsCode; //
        String body = "Bu Eskiz dan test"; // test message
        try {
            sendSms(phone, body);
            smsHistoryService.save(phone, body, String.valueOf(smsCode));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppBadException("Something went wrong");
        }
    sendSms(phone,body);

    }




    private void sendSms(String phone, String message) {
        SmsRequestDto smsRequestDto = new SmsRequestDto();
        smsRequestDto.setMobile_phone(phone);
        smsRequestDto.setMessage(message);

        String url = "https://notify.eskiz.uz/api/message/sms/send";

        //header qismi
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer asasasasa");


        RequestEntity<SmsRequestDto> request = RequestEntity
                .post(url)
                .headers(headers)
                .body(smsRequestDto);


        restTemplate.exchange(request, String.class);


    }


}
