package TopReports.Segurity;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private final String SECRET_KEY = Base64.getEncoder().encodeToString("minhaChaveSegura1234567890123456".getBytes());

    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println("Token extraído: " + token);
            return token;
        }
        return null;
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isValidToken(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
//mudei aqui

public Claims getClaims(String token) {
    try {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    } catch (io.jsonwebtoken.JwtException e) {
        System.err.println("Erro ao processar o token: " + e.getMessage());
        throw new RuntimeException("Token inválido ou corrompido", e);
    }
}
/*mudei aqui
    public String generateToken(String username ) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY_BYTES)
                .compact();
        System.out.println("Token gerado: " + token);
        return token;
    }
*/
    public String generateToken(String username, Long userId) {
        String token = Jwts.builder()
                .setSubject(username)
                .claim("id", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        System.out.println("Token gerado: " + token);
        return token;
    }

    public Authentication getAuthentication(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

}
