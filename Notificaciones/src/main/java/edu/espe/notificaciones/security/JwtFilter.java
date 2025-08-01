package edu.espe.notificaciones.security;



import edu.espe.notificaciones.utils.AESUtil;
import edu.espe.notificaciones.utils.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;


    private AESUtil aesUtil;

    @Autowired
    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        List<String> excludedPaths = List.of(
                "/auth/**",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/actuator/**"
        );

        boolean isExcluded = excludedPaths.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

        if (isExcluded) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = null;
        Cookie[] cookies = request.getCookies();
        log.info(Arrays.toString(cookies));
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("token".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        log.info("Token desde cokie {}",token);

        // Si no se encontró en cookies, buscar en el header Authorization
        if (token == null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }
        }

        if (token == null || !jwtService.isTokenValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Acceso no autorizado: token inválido o ausente");
            return;
        }

        // Extraer datos del token
        String encryptedUsername = jwtService.extractUsername(token);
        String encryptedRole = jwtService.extractClaim(token, claims -> claims.get("role", String.class));

        // Desencriptar los datos

        String username = null;
        try {
            username = aesUtil.decrypt(encryptedUsername, jwtService.getSecretKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String role = null;
        try {
            role = aesUtil.decrypt(encryptedRole, jwtService.getSecretKey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (username != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role); // ← aquí debe venir ROLE_CLIENTE
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    Collections.singletonList(authority)
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
