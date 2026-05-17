package com.simulador.simuladorTactico.services;

import com.simulador.simuladorTactico.persistence.entities.Entrenador;
import com.simulador.simuladorTactico.persistence.repositories.EntrenadorRepository;
import com.simulador.simuladorTactico.services.dto.LoginRequest;
import com.simulador.simuladorTactico.services.dto.LoginResponse;
import com.simulador.simuladorTactico.services.dto.RegisterRequest;
import com.simulador.simuladorTactico.web.config.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy; 
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
    @Lazy 
    private AuthenticationManager authenticationManager;

    
    public void registrar(RegisterRequest request) {
        
        if (!request.getPassword1().equals(request.getPassword2())) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        
        Entrenador entrenador = new Entrenador();
        entrenador.setNombre(request.getNombre());
        entrenador.setCorreo(request.getCorreo());
        entrenador.setRol("ROLE_USER"); 
        
        
        entrenador.setContrasena(passwordEncoder.encode(request.getPassword1()));

        entrenadorRepository.save(entrenador);
    }

    
    public LoginResponse login(LoginRequest request) {
        
        
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getPassword())
        );

        
        UserDetails userDetails = loadUserByUsername(request.getCorreo());
        
        
        String token = jwtUtils.generateToken(userDetails);

        return new LoginResponse(token);
    }

    
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Entrenador entrenador = entrenadorRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Entrenador no encontrado con correo: " + correo));

        
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(entrenador.getCorreo())
                .password(entrenador.getContrasena())
                .authorities(entrenador.getRol())
                .build();
    }
}