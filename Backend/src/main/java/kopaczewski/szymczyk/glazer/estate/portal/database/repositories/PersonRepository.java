package kopaczewski.szymczyk.glazer.estate.portal.database.repositories;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Override
    <S extends Person> S save(S entity);
}