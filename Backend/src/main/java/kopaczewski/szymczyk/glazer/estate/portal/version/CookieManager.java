package kopaczewski.szymczyk.glazer.estate.portal.version;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static kopaczewski.szymczyk.glazer.estate.portal.ConstStorage.AUTHENTICATED_ENDPOINT;

@Component
public class CookieManager {

    @Value("${security.app.cookie.expired.time.ms}")
    private long expirationTime;

    @Value("${security.app.cookie.name}")
    private String cookieName;

    private static final Logger LOGGER = LoggerFactory.getLogger(CookieManager.class);

    private final Algorithm algorithm;


    @Autowired
    public CookieManager(@Value("${security.app.jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC512(secret);
    }


    public Optional<String> getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if (Objects.isNull(cookie)) {
            return Optional.empty();
        } else {
            return Optional.of(cookie.getValue());
        }
    }

    public ResponseCookie generateCookieWithJwt(String login) {
        String jwtToken = createJsonWebToken(login);
        return ResponseCookie
                .from(cookieName, jwtToken)
                .path(AUTHENTICATED_ENDPOINT)
                .maxAge(expirationTime / 1000)
                .httpOnly(true)
                .build();
    }

    public Optional<String> checkJwtValidation(String jwtToken) {
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);
            return Optional.ofNullable(decodedJWT.getSubject());
        } catch (AlgorithmMismatchException e) {
            LOGGER.error("Incorrect token algorithm: ", e);
        } catch (SignatureVerificationException e) {
            LOGGER.error("Invalid token signature: ", e);
        } catch (TokenExpiredException e) {
            LOGGER.error("Token is expired: ", e);
        } catch (MissingClaimException e) {
            LOGGER.error("Token is missing claim: ", e);
        } catch (IncorrectClaimException e) {
            LOGGER.error("Token has incorrect claim: ", e);
        } catch (JWTVerificationException e) {
            LOGGER.debug("Invalid token", e);
        }
        return Optional.empty();
    }

    private String createJsonWebToken(String login) {
        return JWT.create()
                .withSubject(login)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + expirationTime))
                .sign(algorithm);
    }

}
