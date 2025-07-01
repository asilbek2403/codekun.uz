package dasturlashasil.uz.sms;

import dasturlashasil.uz.Dto.sms.SmsHistoryDto;
import dasturlashasil.uz.entities.sms.SmsHistoryEntity;
import dasturlashasil.uz.exceptons.AppBadException;
import dasturlashasil.uz.repository.sms.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class SmsHistoryService {

    @Autowired
    private SmsHistoryRepository smsHistoryRepository;


    public void save(String phone, String body, String code) {
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhoneNumber(phone);
        entity.setBody(body);
        entity.setCode(code);
        entity.setCreatedDate(LocalDateTime.now());
        smsHistoryRepository.save(entity);
    }

    public SmsHistoryEntity getSmsByPhone(String phoneNumber) {
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTopByPhoneNumberOrderByCreatedDateDesc(phoneNumber);
        if (optional.isEmpty()) {
            throw new AppBadException("Invalid phone number");
        }
        return optional.get();
    }

    public void increaseAttempt(String id) {
        smsHistoryRepository.increaseAttempt(id);
    }

    public boolean isSmsSendToPhone(String phone, String code) {
        SmsHistoryEntity smsHistoryEntity = getSmsByPhone(phone);
        // expired time
        LocalDateTime expiredTime = smsHistoryEntity.getCreatedDate().plusMinutes(1);
        // check time
        if (LocalDateTime.now().isAfter(expiredTime)) {
            return false;
        }
        // get attempts
        Integer attempts = smsHistoryEntity.getAttemptCount();
        // check attempt count
        if (attempts >= 5) {
            return false;
        }
        if (!code.equals(smsHistoryEntity.getCode())) {
            //increase attempt count
            increaseAttempt(smsHistoryEntity.getId());
            return false;
        }
        return true;
    }

    public List<SmsHistoryDto> getSmsHistoryByPhone(String phone) {
        // Find entities by toAccount (which is 'email' in the DTO context)
        List<SmsHistoryEntity> entities = smsHistoryRepository.findByPhoneNumber(phone);
        // Convert entities to DTOs and return
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<SmsHistoryDto> getSmsHistoryByDate(LocalDate date) {
        // Calculate the start and end of the given day
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX); // Represents 23:59:59.999999999

        // Find entities created within the specified date range
        List<SmsHistoryEntity> entities = smsHistoryRepository.findByCreatedDateBetween(startOfDay, endOfDay);
        // Convert entities to DTOs and return
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Page<SmsHistoryDto> getPaginatedSmsHistory(int page, int size) {
        // Create a Pageable object for pagination, sorting by createdDate in descending order
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        // Retrieve a page of entities from the repository
        Page<SmsHistoryEntity> entityPage = smsHistoryRepository.findAll(pageable);

        List<SmsHistoryDto> dtoList = entityPage.getContent().stream().map(this::toDto).collect(Collectors.toList());


        // Convert the Page of entities to a Page of DTOs
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    public SmsHistoryDto toDto(SmsHistoryEntity entity) {
        if (entity == null) {
            return null;
        }
        SmsHistoryDto dto = new SmsHistoryDto();
        dto.setId(entity.getId());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setBody(entity.getBody());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }



}
