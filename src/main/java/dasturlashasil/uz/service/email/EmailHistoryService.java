package dasturlashasil.uz.service.email;

import dasturlashasil.uz.entities.emailEntity.EmailHistoryEntity;
import dasturlashasil.uz.repository.emailR.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmailHistoryService {


    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public void create(  String body,Integer code, String toAccoun) {

        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
        emailHistoryEntity.setBody(body);
        emailHistoryEntity.setCode(String.valueOf(code));
        emailHistoryEntity.setToAccount(toAccoun);
        emailHistoryRepository.save(emailHistoryEntity);

    }
}
