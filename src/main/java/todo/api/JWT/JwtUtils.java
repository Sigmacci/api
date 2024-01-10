package todo.api.JWT;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.InvalidKeyException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import todo.api.Helpers.IUserDetails;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${api.jwtSecret}")
    private String jwtSecret;

    @Value("${api.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${api.jwtCookieName}")
    private String jwtCookie;

    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null) {
          return cookie.getValue();
        } else {
          return null;
        }
    }

    public ResponseCookie generateJwtCookie(IUserDetails userDetails)
        throws InvalidKeyException, NoSuchAlgorithmException {
        String jwt = generateTokenFromUsername(userDetails.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true)
          .build();
        return cookie;
    }

    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }

    public String getUserNameFromJwtToken(String token)
        throws JwtException, IllegalArgumentException, NoSuchAlgorithmException {
        return Jwts.parser().verifyWith(key()).build()
          .parseSignedClaims(token).getPayload().getSubject();
    }

    // private String generateJwtSecret() {
    // SecureRandom random = new SecureRandom();
    // byte[] bytes = new byte[32]; // 256 bits
    // random.nextBytes(bytes);
    // return Base64.getEncoder().encodeToString(bytes);
    // }

    private SecretKey key() throws NoSuchAlgorithmException {
        SecretKey secretKey = new SecretKeySpec(jwtSecret.getBytes(), 0, jwtSecret.length(),
        KeyGenerator.getInstance("HmacSHA256").getAlgorithm());
        return secretKey;
    }

    public boolean validateJwtToken(String authToken) throws NoSuchAlgorithmException {
        try {
          Jwts.parser().verifyWith(key()).build().parse(authToken);
          return true;
        } catch (MalformedJwtException e) {
          logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
          logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
          logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
          logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(String username) throws InvalidKeyException, NoSuchAlgorithmException {
        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date())
            .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(key())
            .compact();
    }
}
