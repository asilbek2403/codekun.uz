package dasturlashasil.uz.repository.sms;

import dasturlashasil.uz.entities.sms.SmsHistoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, String>, PagingAndSortingRepository<SmsHistoryEntity, String> {
    Optional<SmsHistoryEntity> findTopByPhoneNumberOrderByCreatedDateDesc(String phoneNumber);

    @Transactional
    @Modifying
    @Query("update SmsHistoryEntity set attemptCount = attemptCount + 1 where id = ?1")
    void increaseAttempt(String id);

    List<SmsHistoryEntity> findByPhoneNumber(String phoneNumber);

    List<SmsHistoryEntity> findByCreatedDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

}