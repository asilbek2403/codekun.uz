package dasturlashasil.uz.repository;

import dasturlashasil.uz.Dto.profile.ProfileFilterDto;
import dasturlashasil.uz.Dto.FilterResultDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Map;

@Repository
public class ProfileCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public  FilterResultDto<Object[]> filter(ProfileFilterDto filterDto , int page, int size){
        //HQL .. Native query !!!

        StringBuilder condition= new StringBuilder(" where visible = true  ");
        Map<String,Object> params = new HashMap<>();

        if (filterDto.getQuery() != null) {
            condition.append(" and (lower(p.name) like :query or lower(p.surname) like :query or lower(p.username) like :query) ");
            params.put("query","%" + filterDto.getQuery().toLowerCase() + "%");
        }// alohida name ,username,surname uchun query qilib olish mumkin!! bu esa bittada yigib olganimiz


        if (filterDto.getRole() != null){
            condition.append(" and  EXISTS ( SELECT 1 FROM profile_role pr WHERE pr.profile_id = p.id and pr.roles = :role) ");
            params.put("role", filterDto.getRole().name() );
//            params.put("query","%" + filterDto.getRole().toLowerCase() + "%");
        }

        if(filterDto.getCreatedDateFrom() != null && filterDto.getCreatedDateTo() != null){
            condition.append(" and pr.created_date between :fromDate and : toDate ");
            params.put("fromDate" , LocalDateTime.of(filterDto.getCreatedDateFrom() ,LocalTime.MIN ));
            params.put("toDate" , LocalDateTime.of(filterDto.getCreatedDateTo() ,LocalTime.MAX ));
        }
        else if (filterDto.getCreatedDateFrom() != null){
            condition.append(" and pr.created_date >= :fromDate ");// 01.01.2021
            params.put("fromDate" , LocalDateTime.of(filterDto.getCreatedDateFrom(),LocalTime.MIN) );
        }
        else if(filterDto.getCreatedDateTo() != null){
            condition.append(" and pr.created_date <= : toDate ");// 01.01.2024
            params.put("toDate" , LocalDateTime.of(filterDto.getCreatedDateTo() ,LocalTime.MAX));
        }

        StringBuilder selectBuilder = new StringBuilder("Select p.id as id,p.name as name,p.surname as surname," +
                " p.username as username, p.status as status, p.created_date as createdDate," +
                " ARRAY_AGG(pr.roles) AS roles  " +
                " From profile p ");
        selectBuilder.append(" left join profile_role as pr on pr.profile_id = p.id ");




        StringBuilder countBuilder = new StringBuilder("Select count(*) From profile p ");
//        conditions

        selectBuilder.append(condition);
        countBuilder.append(condition);
        // group by
        selectBuilder.append(" GROUP BY p.id ,p.name , p.surname , p.username , p.status, p.created_date " );
        countBuilder.append(" GROUP BY p.id ,p.name ,p.surname, p.username , p.status , p.created_date");


        Query selectQuery=entityManager.createNativeQuery(selectBuilder.toString());
        Query countQuery = entityManager.createNativeQuery(countBuilder.toString());


        for (Map.Entry<String,Object> entry : params.entrySet()) {
            selectQuery.setParameter(entry.getKey(), entry.getValue());
            countQuery.setParameter(entry.getKey(), entry.getValue());
        }

        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult(page * size); // offset


        List<Object[]> profileList = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();



        return new FilterResultDto<>(profileList, totalCount);
    }


}
