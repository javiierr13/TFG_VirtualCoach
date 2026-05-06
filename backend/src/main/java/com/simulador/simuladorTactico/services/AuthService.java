package com.simulador.simuladorTactico.services;

import com.simulador.simuladorTactico.persistence.entities.Entrenador;
import com.simulador.simuladorTactico.persistence.repositories.EntrenadorRepository;
import com.simulador.simuladorTactico.services.dto.LoginRequest;
import com.simulador.simuladorTactico.services.dto.LoginResponse;
import com.simulador.simuladorTactico.services.dto.RegisterRequest;
import com.simulador.simuladorTactico.web.config.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy; // <--- IMPORTANTE: Necesario para @Lazy
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    @Lazy // Rompe el ciclo de dependencia circular
    private AuthenticationManager authenticationManager;

    /**
     * REGISTRO: Crea un nuevo entrenador en la base de datos
     */
    public void registrar(RegisterRequest request) {
        // 1. Validar que las contraseñas coinciden
        if (!request.getPassword1().equals(request.getPassword2())) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        // 2. Crear el nuevo entrenador
        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(request.getNombre());
        entrenador.setCorreo(request.getCorreo());
        entrenador.setRol("ROLE_USER"); // Asignamos rol por defecto
        
        // 3. Encriptar la contraseña
        entrenador.setContrasena(passwordEncoder.encode(request.getPassword1()));

        entrenadorRepository.save(entrenador);
    }

    /**
     * LOGIN: Autentica y devuelve el Token JWT
     */
    public LoginResponse login(LoginRequest request) {
        // 1. Autenticar usando el gestor de Spring Security.
        // Gracias a @Lazy, esto ya no bloqueará el arranque.
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        // 2. Recuperar el usuario para generar el token
        UserDetails userDetails = loadUserByUsername(request.getCorreo());
        
        // 3. Generar el token
        String token = jwtUtils.generateToken(userDetails);

        return new LoginResponse(token);
    }

    /**
     * OBLIGATORIO: Método que usa Spring Security para buscar usuarios en TU base de datos
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Entrenador entrenador = entrenadorRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Entrenador no encontrado con correo: " + correo));

        // Convertimos tu entidad 'Entrenador' a un objeto 'User' que Spring Security entienda
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(entrenador.getCorreo())
                .password(entrenador.getContrasena())
                .authorities(entrenador.getRol())
                .build();
    }
}