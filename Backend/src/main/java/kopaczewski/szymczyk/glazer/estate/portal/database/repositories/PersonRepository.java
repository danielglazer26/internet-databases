package kopaczewski.szymczyk.glazer.estate.portal.database.repositories;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Person;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @NotNull
    @Override
    <S extends Person> S save(@NotNull S entity);

    @Query("select p from Person p where p.login = ?1")
    Optional<Person> findByLogin(@Nullable String login);

    @Query(nativeQuery = true, value = "select * from Person p where p.role != 0")
    List<Person> getAllUsers();
}