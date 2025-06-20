package dasturlashasil.uz.repository;

import dasturlashasil.uz.entities.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {
    //@NotBlank(message = "Username  bo‘sh bo‘lmasligi kerak")
    Optional<ProfileEntity> findByUsernameAndVisibleIsTrue(String username);

    Optional<ProfileEntity> findByIdAndVisibleIsTrue(Integer id);

    Page<ProfileEntity> findAllByVisibleTrue(Pageable pageable);

}