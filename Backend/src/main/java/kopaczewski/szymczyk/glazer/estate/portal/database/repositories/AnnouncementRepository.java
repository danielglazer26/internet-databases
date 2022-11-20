package kopaczewski.szymczyk.glazer.estate.portal.database.repositories;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Override
    <S extends Announcement> S save(S entity);


    @Query(nativeQuery = true, value = """
            select * from Announcement a where
                (:minCost is null OR a.cost_per_month > :minCost ) AND
                (:maxCost is null OR a.cost_per_month < :maxCost ) AND
                (:minArea is null OR a.area > :minArea ) AND
                (:maxArea is null OR a.area < :maxArea ) AND
                (:roomNumber is null OR a.room_number = :roomNumber) AND
                (:city is null OR a.city = :city) AND
                (:street is null OR a.street = :street) AND
                (:apartmentNumber is null OR a.apartment_number = :apartmentNumber)
            LIMIT :limit OFFSET :offset""")
    List<Announcement> findFiltered(
            @Param("minCost") Integer minCost, @Param("maxCost") Integer maxCost,
            @Param("minArea") Double minArea, @Param("maxArea") Double maxArea,
            @Param("roomNumber") Integer roomNumber,
            @Param("city") String city,
            @Param("street") String street,
            @Param("apartmentNumber") Integer apartmentNumber,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset);

    @Query(nativeQuery = true, value = "select *  from Announcement  LIMIT :limit OFFSET :offset")
    List<Announcement> findAll(@Param("limit") Integer limit, @Param("offset") Integer offset);
}