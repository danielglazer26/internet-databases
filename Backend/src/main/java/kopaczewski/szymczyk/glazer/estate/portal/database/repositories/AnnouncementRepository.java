package kopaczewski.szymczyk.glazer.estate.portal.database.repositories;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.Announcement;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.AnnouncementType;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.announcement.ProvinceNameEnum;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @NotNull
    @Override
    <S extends Announcement> S save(@NotNull S entity);


    @Query(nativeQuery = true, value = """
            select * from Announcement a where
                (:minCost is null OR a.cost_per_month > :minCost ) AND
                (:maxCost is null OR a.cost_per_month < :maxCost ) AND
                (:minArea is null OR a.area > :minArea ) AND
                (:maxArea is null OR a.area < :maxArea ) AND
                (:roomNumber is null OR a.room_number = :roomNumber) AND
                (:city is null OR a.city = :city) AND
                (:street is null OR a.street = :street) AND
                (:apartmentNumber is null OR a.apartment_number = :apartmentNumber) AND
                (:announcementType is null OR a.announcementType = :announcementType)
            LIMIT :limit OFFSET :offset""")
    List<Announcement> findFiltered(
            @Param("minCost") Integer minCost, @Param("maxCost") Integer maxCost,
            @Param("minArea") Double minArea, @Param("maxArea") Double maxArea,
            @Param("roomNumber") Integer roomNumber,
            @Param("city") String city,
            @Param("street") String street,
            @Param("apartmentNumber") Integer apartmentNumber,
            @Param("announcementType") AnnouncementType announcementType,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset);

    @Query(nativeQuery = true, value = "select *  from Announcement  LIMIT :limit OFFSET :offset")
    List<Announcement> findAll(@Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query("select a from Announcement a where a.announcementId = :id")
    Optional<Announcement> findAnnouncementByAnnouncementId(@Param("id")Long id);
    @Modifying
    @Query("update Announcement set" +
            " title = :nTitle," +
            " additionalDescription = :nDescription," +
            " announcementType = :nAnnouncementType," +
            " apartmentNumber = :nApartmentNumber," +
            " street = :nStreet," +
            " city = :nCity," +
            " province = :nProvince," +
            " costPerMonth = :nCostPerMonth," +
            " rent = :nRent," +
            " deposit = :nDeposit," +
            " roomNumber = :nRoomNumber," +
            " area = :nArea " +
            " where announcementId = :id")
    void updateAnnouncement(
            @Param("id") Long announcementId,
            @Param("nTitle") String title,
            @Param("nDescription") String additionalDescription,
            @Param("nAnnouncementType") AnnouncementType announcementType,
            @Param("nApartmentNumber") String apartmentNumber,
            @Param("nStreet") String street,
            @Param("nCity") String city,
            @Param("nProvince") ProvinceNameEnum province,
            @Param("nCostPerMonth") Integer costPerMonth,
            @Param("nRent") Integer rent,
            @Param("nDeposit") Integer deposit,
            @Param("nRoomNumber") Integer roomNumber,
            @Param("nArea") Double area);
}