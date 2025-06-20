package dasturlashasil.uz.repository;

import dasturlashasil.uz.entities.RegionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    List<RegionEntity> findAllByVisibleTrue();

    Optional<RegionEntity> findByOrderNum(Integer number);

}

