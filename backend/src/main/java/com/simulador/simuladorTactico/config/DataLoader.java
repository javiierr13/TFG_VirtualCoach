package com.simulador.simuladorTactico.config;

import com.simulador.simuladorTactico.persistence.entities.*;
import com.simulador.simuladorTactico.persistence.enums.*;
import com.simulador.simuladorTactico.persistence.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private EntrenadorRepository entrenadorRepository;

    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private AlineacionRepository alineacionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        String testEmail = "admin@test.com";
        if (entrenadorRepository.findByCorreo(testEmail).isEmpty()) {
            Entrenador admin = new Entrenador();
            admin.setNombre("Javier Ruiz (Test)");
            admin.setCorreo(testEmail);
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setRol("ROLE_USER");
            admin = entrenadorRepository.save(admin);

            
            List<Jugador> equipo = createSamplePlayers(admin);
            jugadorRepository.saveAll(equipo);

            
            createSampleAlignmentF11(admin, equipo);
            createSampleAlignmentF7(admin, equipo);
            createSampleAlignmentSala(admin, equipo);
            
            System.out.println(">>> DATOS DE PRUEBA CARGADOS CORRECTAMENTE <<<");
            System.out.println("Usuario: " + testEmail + " / Pass: admin123");
        }
    }

    private List<Jugador> createSamplePlayers(Entrenador entrenador) {
        List<Jugador> jugadores = new ArrayList<>();
        
        
        jugadores.add(createPlayer("Iker Casillas", 1, Posicion.PORTERO, PiernaDominante.DIESTRO, entrenador));
        
        
        jugadores.add(createPlayer("Sergio Ramos", 4, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Carles Puyol", 5, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Jordi Alba", 18, Posicion.DEFENSA, PiernaDominante.ZURDO, entrenador));
        jugadores.add(createPlayer("Dani Carvajal", 2, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        
        
        jugadores.add(createPlayer("Andrés Iniesta", 6, Posicion.MEDIOCENTRO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Xavi Hernández", 8, Posicion.MEDIOCENTRO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Sergio Busquets", 5, Posicion.MEDIOCENTRO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("David Silva", 21, Posicion.MEDIOCENTRO, PiernaDominante.ZURDO, entrenador));
        
        
        jugadores.add(createPlayer("Fernando Torres", 9, Posicion.DELANTERO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("David Villa", 7, Posicion.DELANTERO, PiernaDominante.DIESTRO, entrenador));

        return jugadores;
    }

    private Jugador createPlayer(String nombre, int dorsal, Posicion pos, PiernaDominante pierna, Entrenador ent) {
        Jugador j = new Jugador();
        j.setNombre(nombre);
        j.setDorsal(dorsal);
        j.setPosicion(pos);
        j.setPiernaDominante(pierna);
        j.setEntrenador(ent);
        return j;
    }

    private void createSampleAlignmentF11(Entrenador entrenador, List<Jugador> equipo) {
        Alineacion alineacion = new Alineacion();
        alineacion.setTipoFormacion("4-4-2 Clásica");
        alineacion.setTipoFutbol(TipoFutbol.FUTBOL_11);
        alineacion.setEntrenador(entrenador);
        alineacion.setPosiciones(new ArrayList<>());

        
        addParticipacion(alineacion, equipo.get(0), 5, 50); 
        
        
        addParticipacion(alineacion, equipo.get(3), 20, 15); 
        addParticipacion(alineacion, equipo.get(1), 20, 40); 
        addParticipacion(alineacion, equipo.get(2), 20, 60); 
        addParticipacion(alineacion, equipo.get(4), 20, 85); 
        
        
        addParticipacion(alineacion, equipo.get(5), 45, 20); 
        addParticipacion(alineacion, equipo.get(7), 40, 50); 
        addParticipacion(alineacion, equipo.get(6), 45, 80); 
        addParticipacion(alineacion, equipo.get(8), 60, 50); 
        
        
        addParticipacion(alineacion, equipo.get(9), 85, 35); 
        addParticipacion(alineacion, equipo.get(10), 85, 65); 

        alineacionRepository.save(alineacion);
    }

    private void createSampleAlignmentF7(Entrenador entrenador, List<Jugador> equipo) {
        Alineacion alineacion = new Alineacion();
        alineacion.setTipoFormacion("3-2-1 (Sólida)");
        alineacion.setTipoFutbol(TipoFutbol.FUTBOL_7);
        alineacion.setEntrenador(entrenador);
        alineacion.setPosiciones(new ArrayList<>());

        
        addParticipacion(alineacion, equipo.get(0), 5, 50);
        
        addParticipacion(alineacion, equipo.get(1), 25, 50); 
        addParticipacion(alineacion, equipo.get(3), 25, 20); 
        addParticipacion(alineacion, equipo.get(4), 25, 80); 
        
        addParticipacion(alineacion, equipo.get(5), 55, 30); 
        addParticipacion(alineacion, equipo.get(6), 55, 70);
        
        addParticipacion(alineacion, equipo.get(9), 85, 50);

        alineacionRepository.save(alineacion);
    }

    private void createSampleAlignmentSala(Entrenador entrenador, List<Jugador> equipo) {
        Alineacion alineacion = new Alineacion();
        alineacion.setTipoFormacion("1-2-1 (Rombo)");
        alineacion.setTipoFutbol(TipoFutbol.FUTBOL_SALA);
        alineacion.setEntrenador(entrenador);
        alineacion.setPosiciones(new ArrayList<>());

        
        addParticipacion(alineacion, equipo.get(0), 5, 50);
        
        addParticipacion(alineacion, equipo.get(1), 25, 50); 
        
        addParticipacion(alineacion, equipo.get(5), 50, 20); 
        addParticipacion(alineacion, equipo.get(6), 50, 80);
        
        addParticipacion(alineacion, equipo.get(9), 80, 50);

        alineacionRepository.save(alineacion);
    }

    private void addParticipacion(Alineacion alineacion, Jugador jugador, int x, int y) {
        Participa p = new Participa();
        p.setAlineacion(alineacion);
        p.setJugador(jugador);
        p.setPosX(x);
        p.setPosY(y);
        alineacion.getPosiciones().add(p);
    }
}
