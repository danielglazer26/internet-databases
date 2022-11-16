package kopaczewski.szymczyk.glazer.estate.portal.database.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;
    @OneToMany
    @JoinColumn(name = "announcementId")
    private Set<Announcement> announcements;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password, email;
    private String name, surname, nip, phoneNumber;
}
