package com.simulador.simuladorTactico.web.config;

import com.simulador.simuladorTactico.services.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    @Lazy // Usamos Lazy aquí para evitar el ciclo de dependencias 
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtener el header de autorización: "Bearer"
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Si no hay token, dejamos pasar la petición (SecurityConfig se encargará de rechazarla si es necesario)
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Limpiar el token 
        String jwt = authHeader.substring(7);

        // 3. Extraer el usuario del token
        String userEmail = jwtUtils.extractUsername(jwt);

        // 4. Si hay usuario y nadie está autenticado todavía en el contexto...
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Cargamos los detalles del usuario desde la base de datos
            UserDetails userDetails = this.authService.loadUserByUsername(userEmail);

            // 5. Crear el objeto de autenticación final
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 6. Meter al usuario en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // 7. Continuar con la petición
        filterChain.doFilter(request, response);
    }
}