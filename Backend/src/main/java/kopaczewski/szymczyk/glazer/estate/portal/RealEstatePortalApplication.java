package kopaczewski.szymczyk.glazer.estate.portal;

import kopaczewski.szymczyk.glazer.estate.portal.database.model.Announcement;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.Person;
import kopaczewski.szymczyk.glazer.estate.portal.database.repositories.AnnouncementRepository;
import kopaczewski.szymczyk.glazer.estate.portal.database.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class RealEstatePortalApplication {
@Autowired
PersonRepository personRepository;
@Autowired
AnnouncementRepository announcementRepository;
	public static void main(String[] args) {
		SpringApplication.run(RealEstatePortalApplication.class, args);
	}

	@PostConstruct
	void init(){
		var per = new Person(0L, null,"login","test","test","test","test","test","test");
		personRepository.save(per);
		List<Person> all = personRepository.findAll();
		var ano = new Announcement(0L,"A0Test","test",all.get(0),3,"test","test",10,10,10,10,null);

		announcementRepository.save(ano);
		all = personRepository.findAll();
//		List<Announcement> repositoryAll = announcementRepository.findAll();
//		System.out.println(repositoryAll);
		System.out.println(all);
	}
}
