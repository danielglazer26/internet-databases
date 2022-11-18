package kopaczewski.szymczyk.glazer.estate.portal.controller.authorization;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String login;
    private String password;
    private String email;
}
