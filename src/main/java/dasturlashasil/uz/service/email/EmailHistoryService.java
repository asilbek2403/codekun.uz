package dasturlashasil.uz.service.email;

import dasturlashasil.uz.Dto.emailDto.EmailHistoryDto;
import dasturlashasil.uz.entities.emailEntity.EmailHistoryEntity;
import dasturlashasil.uz.repository.emailR.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

//    public List<EmailHistoryDto> getEmailHistoryByEmail(String email) {
//        // Find entities by toAccount (which is 'email' in the DTO context)
//        List<EmailHistoryEntity> entities = emailHistoryRepository.findByToAccount(email);
//        // Convert entities to DTOs and return
//        return entities.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
//    }
//
//    public List<EmailHistoryDto> getEmailHistoryByDate(LocalDate date) {
//        // Calculate the start and end of the given day
//        LocalDateTime startOfDay = date.atStartOfDay();
//        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // Represents 23:59:59.999999999
//
//        // Find entities created within the specified date range
//        List<EmailHistoryEntity> entities = emailHistoryRepository.findByCreatedDateBetween(startOfDay, endOfDay);
//        // Convert entities to DTOs and return
//        return entities.stream().map(entity -> toDto(entity)).collect(Collectors.toList());
//    }
//
//    public Page<EmailHistoryDto> getPaginatedEmailHistory(int page, int size) {
//        // Create a Pageable object for pagination, sorting by createdDate in descending order
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
//
//        // Retrieve a page of entities from the repository
//        Page<EmailHistoryEntity> entityPage = emailHistoryRepository.findAll(pageable);
//
//        List<EmailHistoryDto> dtoList = entityPage.getContent().stream().map(entity -> toDto(entity)).collect(Collectors.toList());
//
//
//        // Convert the Page of entities to a Page of DTOs
//        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
//    }
//
//    public EmailHistoryDto toDto(EmailHistoryEntity entity) {
//        if (entity == null) {
//            return null;
//        }
//        EmailHistoryDto dto = new EmailHistoryDto();
//        dto.setId(entity.getId());
//        dto.setEmail(entity.getToAccount());
//        dto.setBody(entity.getBody());
//        dto.setCreatedDate(entity.getCreatedDate());
//        return dto;
//    }


}
