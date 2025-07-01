package dasturlashasil.uz.repository;

import dasturlashasil.uz.entities.AttachEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachRepository extends JpaRepository<AttachEntity, String> {

    @Query("from AttachEntity where visible = true order by createdDate desc")
    Page<AttachEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);

}
