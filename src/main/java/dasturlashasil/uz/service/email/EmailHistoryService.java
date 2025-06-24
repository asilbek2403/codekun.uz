package dasturlashasil.uz.service.email;

import dasturlashasil.uz.entities.emailEntity.EmailHistoryEntity;
import dasturlashasil.uz.repository.emailR.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
//import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmailHistoryService {


    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public void create(  String body,Integer code, String toAccoun) {

        EmailHistoryEntity emailHistoryEntity = new EmailHistoryEntity();
        emailHistoryEntity.setBody(body);
        emailHistoryEntity.setCode(code);
        emailHistoryEntity.setToAccount(toAccoun);
        emailHistoryRepository.save(emailHistoryEntity);

    }

    public Boolean isSmsSendToAccount(String account, int code){

        Optional<EmailHistoryEntity> entity= emailHistoryRepository.findTopByToAccountOrderByCreatedDateDesc( account );
        if(entity.isEmpty()){
            return false;
        }
        EmailHistoryEntity emailHistoryEntity = entity.get();
        if(!emailHistoryEntity.getCode().equals(code)){
            return false;
        }

        LocalDateTime extDate = entity.get().getCreatedDate().plusMinutes(2);
        if(LocalDateTime.now().isAfter(extDate)){
            return false;
        }
        return true;
    }



}
