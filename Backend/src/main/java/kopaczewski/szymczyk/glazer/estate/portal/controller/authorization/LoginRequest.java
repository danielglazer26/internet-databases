package kopaczewski.szymczyk.glazer.estate.portal.controller.authorization;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String login;
    private String password;
}
