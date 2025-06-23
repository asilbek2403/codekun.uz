package dasturlashasil.uz.repository.emailR;

import dasturlashasil.uz.entities.emailEntity.EmailHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity,String> {

}
