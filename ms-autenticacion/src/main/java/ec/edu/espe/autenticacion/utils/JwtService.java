package ec.edu.espe.autenticacion.utils;

import ec.edu.espe.autenticacion.entity.AuthenticatorUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private  String SECRET_KEY; // mínimo 32 caracteres

    private final long EXPIRATION = 1000 * 60 * 60; // 1 hora

    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private String hashSha256ToBase64(String input) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha256.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error generando hash SHA-256", e);
        }
    }
    public String getSecretKey() {
        return SECRET_KEY;
    }

    public String generateToken(AuthenticatorUser authenticatorUser) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        String encryptedRole = AESUtil.encrypt(
                "ROLE_" + authenticatorUser.getID_ROLE().getNombreRole().toUpperCase(),
                SECRET_KEY
        );



        claims.put("role", encryptedRole);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(AESUtil.encrypt(authenticatorUser.getID_CEDULA_USUARIO(), SECRET_KEY))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public UserDetails extractUserDetails(String token) {
        String username = extractUsername(token);
        String role = extractRole(token);
        log.info("username {} role {}", username, role);

        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("El token no contiene un rol válido.");
        }

        return User.withUsername(username)
                .password("") // sin contraseña porque usamos JWT
                .authorities("ROLE_" + role.toUpperCase()) // Spring espera "ROLE_ADMINISTRADOR", etc.
                .build();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.error("Token inválido: {}", e.getMessage()); // ← esto te mostrará "Invalid signature"
            return false;
        }
    }
}