package dasturlashasil.uz.repository;

import dasturlashasil.uz.entities.RegionEntity;
import dasturlashasil.uz.mapperL.RegionMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    List<RegionEntity> findAllByVisibleTrue();

    Optional<RegionEntity> findByOrderNum(Integer number);


    @Query("SELECT c.id AS id, " +
            "CASE :lang " +
            "   WHEN 'UZ' THEN c.nameUz " +
            "   WHEN 'RU' THEN c.nameRu " +
            "   WHEN 'EN' THEN c.nameEn " +
            "END AS name, " +
            "c.key AS regionKey " +
            "FROM RegionEntity c " +
            "WHERE c.visible = true and c.id = :id")
    Optional<RegionMapper> getByIdAndLang(@Param("id") Integer id, @Param("lang") String lang);
}

