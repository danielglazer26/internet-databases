package kopaczewski.szymczyk.glazer.estate.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class RealEstatePortalApplication {
	public static void main(String[] args) {
		SpringApplication.run(RealEstatePortalApplication.class, args);
	}

	@PostConstruct
	void init(){

	}
}
