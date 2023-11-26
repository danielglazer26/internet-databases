package bednarz.glazer.iwaniak.estate.portal.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    @Column(nullable = false, unique = true)
    private String login;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;
    private String name, surname, nip, phoneNumber;
    private Roles role;

}
