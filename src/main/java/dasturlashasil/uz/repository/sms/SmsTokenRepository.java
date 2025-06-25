package dasturlashasil.uz.repository.sms;

import dasturlashasil.uz.entities.sms.SmsTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SmsTokenRepository extends CrudRepository<SmsTokenEntity, Integer> {
    Optional<SmsTokenEntity> findTopByOrderByCreatedDateDesc();
}

