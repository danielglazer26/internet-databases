package bednarz.glazer.iwaniak.estate.portal.database.services;

import bednarz.glazer.iwaniak.estate.portal.database.model.Person;
import bednarz.glazer.iwaniak.estate.portal.database.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {


    private final PersonRepository personRepository;

    @Autowired
    public MyUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Person> person = personRepository.findByLogin(username);

        if (person.isPresent()) {
            return new AccountData(person.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}