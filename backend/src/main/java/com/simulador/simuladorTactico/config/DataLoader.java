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
            createSampleAlignmentCornerF7(admin, equipo);
            createSampleAlignmentPresionSala(admin, equipo);
            createSampleAlignmentContragolpeF11(admin, equipo);
            
            System.out.println(">>> DATOS DE PRUEBA DE ADMIN CARGADOS CORRECTAMENTE <<<");
        }

        
        String vladiEmail = "vladi@gmail.com";
        if (entrenadorRepository.findByCorreo(vladiEmail).isEmpty()) {
            Entrenador vladi = new Entrenador();
            vladi.setNombre("Vladi (Betis)");
            vladi.setCorreo(vladiEmail);
            vladi.setContrasena(passwordEncoder.encode("betis69"));
            vladi.setRol("ROLE_USER");
            vladi = entrenadorRepository.save(vladi);

            List<Jugador> equipoBetis = createBetisPlayers(vladi);
            jugadorRepository.saveAll(equipoBetis);

            createSampleAlignmentF11(vladi, equipoBetis);
            createSampleAlignmentF7(vladi, equipoBetis);
            createSampleAlignmentSala(vladi, equipoBetis);
            
            System.out.println(">>> DATOS DE PRUEBA DE VLADI CARGADOS CORRECTAMENTE (betis69) <<<");
        }

        
        String octavioEmail = "octavio@gmail.com";
        if (entrenadorRepository.findByCorreo(octavioEmail).isEmpty()) {
            Entrenador octavio = new Entrenador();
            octavio.setNombre("Octavio (Pokemon)");
            octavio.setCorreo(octavioEmail);
            octavio.setContrasena(passwordEncoder.encode("octaviomaraton"));
            octavio.setRol("ROLE_USER");
            octavio = entrenadorRepository.save(octavio);

            List<Jugador> equipoPokemon = createPokemonPlayers(octavio);
            jugadorRepository.saveAll(equipoPokemon);

            createSampleAlignmentF11(octavio, equipoPokemon);
            createSampleAlignmentF7(octavio, equipoPokemon);
            createSampleAlignmentSala(octavio, equipoPokemon);
            
            System.out.println(">>> DATOS DE PRUEBA DE OCTAVIO CARGADOS CORRECTAMENTE (octaviomaraton) <<<");
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

    private void createSampleAlignmentCornerF7(Entrenador entrenador, List<Jugador> equipo) {
        Alineacion alineacion = new Alineacion();
        alineacion.setNombre("Córner Ofensivo Jugada");
        alineacion.setTipoFormacion("3-2-1 (Sólida)");
        alineacion.setTipoFutbol(TipoFutbol.FUTBOL_7);
        alineacion.setEntrenador(entrenador);
        alineacion.setPosiciones(new ArrayList<>());

        
        addParticipacion(alineacion, equipo.get(0), 8, 50);
        
        addParticipacion(alineacion, equipo.get(1), 35, 50);
        
        addParticipacion(alineacion, equipo.get(8), 98, 98);
        
        addParticipacion(alineacion, equipo.get(5), 85, 30);
        
        addParticipacion(alineacion, equipo.get(6), 88, 70);
        
        addParticipacion(alineacion, equipo.get(9), 80, 50);
        
        addParticipacion(alineacion, equipo.get(10), 68, 50);

        alineacionRepository.save(alineacion);
    }

    private void createSampleAlignmentPresionSala(Entrenador entrenador, List<Jugador> equipo) {
        Alineacion alineacion = new Alineacion();
        alineacion.setNombre("Presión Alta 1-1-2");
        alineacion.setTipoFormacion("1-2-1");
        alineacion.setTipoFutbol(TipoFutbol.FUTBOL_SALA);
        alineacion.setEntrenador(entrenador);
        alineacion.setPosiciones(new ArrayList<>());

        
        addParticipacion(alineacion, equipo.get(0), 10, 50);
        
        addParticipacion(alineacion, equipo.get(1), 38, 50);
        
        addParticipacion(alineacion, equipo.get(5), 58, 25);
        
        addParticipacion(alineacion, equipo.get(6), 58, 75);
        
        addParticipacion(alineacion, equipo.get(9), 78, 50);

        alineacionRepository.save(alineacion);
    }

    private void createSampleAlignmentContragolpeF11(Entrenador entrenador, List<Jugador> equipo) {
        Alineacion alineacion = new Alineacion();
        alineacion.setNombre("Contragolpe Rápido");
        alineacion.setTipoFormacion("5-4-1");
        alineacion.setTipoFutbol(TipoFutbol.FUTBOL_11);
        alineacion.setEntrenador(entrenador);
        alineacion.setPosiciones(new ArrayList<>());

        
        addParticipacion(alineacion, equipo.get(0), 8, 50);
        
        addParticipacion(alineacion, equipo.get(3), 18, 20);
        addParticipacion(alineacion, equipo.get(1), 15, 38);
        addParticipacion(alineacion, equipo.get(2), 12, 50);
        addParticipacion(alineacion, equipo.get(4), 15, 62);
        addParticipacion(alineacion, equipo.get(7), 18, 80);
        
        addParticipacion(alineacion, equipo.get(5), 35, 25);
        addParticipacion(alineacion, equipo.get(6), 30, 45);
        addParticipacion(alineacion, equipo.get(8), 30, 55);
        addParticipacion(alineacion, equipo.get(10), 35, 75);
        
        addParticipacion(alineacion, equipo.get(9), 60, 50);

        alineacionRepository.save(alineacion);
    }

    private List<Jugador> createBetisPlayers(Entrenador entrenador) {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(createPlayer("Claudio Bravo", 1, Posicion.PORTERO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Rui Silva", 13, Posicion.PORTERO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Germán Pezzella", 20, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Marc Bartra", 15, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Héctor Bellerín", 2, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Juan Miranda", 3, Posicion.DEFENSA, PiernaDominante.ZURDO, entrenador));
        jugadores.add(createPlayer("Abner Vinícius", 25, Posicion.DEFENSA, PiernaDominante.ZURDO, entrenador));
        jugadores.add(createPlayer("Guido Rodríguez", 21, Posicion.MEDIOCENTRO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Marc Roca", 4, Posicion.MEDIOCENTRO, PiernaDominante.ZURDO, entrenador));
        jugadores.add(createPlayer("William Carvalho", 14, Posicion.MEDIOCENTRO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Nabil Fekir", 8, Posicion.MEDIOCENTRO, PiernaDominante.ZURDO, entrenador));
        jugadores.add(createPlayer("Isco Alarcón", 22, Posicion.MEDIOCENTRO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Ayoze Pérez", 10, Posicion.DELANTERO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Willian José", 12, Posicion.DELANTERO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Assane Diao", 38, Posicion.DELANTERO, PiernaDominante.DIESTRO, entrenador));
        return jugadores;
    }

    private List<Jugador> createPokemonPlayers(Entrenador entrenador) {
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(createPlayer("Pikachu", 1, Posicion.PORTERO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Squirtle", 13, Posicion.PORTERO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Blastoise", 9, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Venusaur", 3, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Snorlax", 10, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Mewtwo", 15, Posicion.MEDIOCENTRO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Lucario", 8, Posicion.MEDIOCENTRO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Gengar", 7, Posicion.MEDIOCENTRO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Eevee", 5, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Greninja", 11, Posicion.DELANTERO, PiernaDominante.ZURDO, entrenador));
        jugadores.add(createPlayer("Dragonite", 14, Posicion.DELANTERO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Alakazam", 12, Posicion.MEDIOCENTRO, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Machamp", 4, Posicion.DEFENSA, PiernaDominante.DIESTRO, entrenador));
        jugadores.add(createPlayer("Gardevoir", 6, Posicion.MEDIOCENTRO, PiernaDominante.ZURDO, entrenador));
        jugadores.add(createPlayer("Rayquaza", 99, Posicion.DELANTERO, PiernaDominante.DIESTRO, entrenador));
        return jugadores;
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
