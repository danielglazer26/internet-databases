package kopaczewski.szymczyk.glazer.estate.portal.database.repositories;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Override
    <S extends Announcement> S save(S entity);
}