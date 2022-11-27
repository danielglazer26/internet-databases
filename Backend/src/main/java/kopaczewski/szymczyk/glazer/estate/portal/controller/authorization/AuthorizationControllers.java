package kopaczewski.szymczyk.glazer.estate.portal.controller.authorization;


import kopaczewski.szymczyk.glazer.estate.portal.connection.settings.CookieManager;
import kopaczewski.szymczyk.glazer.estate.portal.controller.authorization.requests.LoginRequest;
import kopaczewski.szymczyk.glazer.estate.portal.controller.authorization.requests.RegisterRequest;
import kopaczewski.szymczyk.glazer.estate.portal.controller.ResponseJsonBody;
import kopaczewski.szymczyk.glazer.estate.portal.database.model.Person;
import kopaczewski.szymczyk.glazer.estate.portal.database.services.AccountData;
import kopaczewski.szymczyk.glazer.estate.portal.database.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthorizationControllers {

    private final PersonService personService;
    private final AuthenticationManager authenticationManager;
    private final CookieManager cookieManager;

    @Autowired
    public AuthorizationControllers(PersonService personService, AuthenticationManager authenticationManager, CookieManager cookieManager) {
        this.personService = personService;
        this.authenticationManager = authenticationManager;
        this.cookieManager = cookieManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterRequest registerRequest) {
        String login = registerRequest.login();

        if (personService.getPersonByLogin(login).isEmpty()) {
            Optional<Person> newUser = personService.createNewPerson(login,
                    BCrypt.hashpw(registerRequest.password(), BCrypt.gensalt()),
                    registerRequest.email());
            return newUser.isPresent() ?
                    ResponseEntity.ok(new ResponseJsonBody("Create user " + login)) :
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseJsonBody("Error occurs " +
                            "when add new user to database"));
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseJsonBody("This login exist: " + login));
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginToAccount(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.login(),
                    loginRequest.password()));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(new ResponseJsonBody("Incorrect credentials"));
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        AccountData accountData = (AccountData) authentication.getPrincipal();

        ResponseCookie responseCookie = cookieManager.generateCookieWithJwt(accountData.getUsername());
        
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(personService.getPersonByLogin(loginRequest.login()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutFromAccount() {
        ResponseCookie responseCookie = cookieManager.generateEmptyCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new ResponseJsonBody("You have successfully logout out"));
    }
}
