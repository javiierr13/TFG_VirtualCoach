package com.simulador.simuladorTactico.services;

import com.simulador.simuladorTactico.services.dto.CoordenadaDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FormacionRivalService {

    // Diccionario donde guardamos las alineaciones en memoria
    private final Map<String, List<CoordenadaDTO>> catalogo = new HashMap<>();

    public FormacionRivalService() {
        inicializarDatos();
    }

    private void inicializarDatos() {
        // --- FÚTBOL 11: 4-4-2 ---
        List<CoordenadaDTO> f11_442 = new ArrayList<>();
        f11_442.add(new CoordenadaDTO("POR", 50, 5));  // Portero 
        f11_442.add(new CoordenadaDTO("LD", 80, 20));  // Defensas
        f11_442.add(new CoordenadaDTO("CT", 60, 15));
        f11_442.add(new CoordenadaDTO("CT", 40, 15));
        f11_442.add(new CoordenadaDTO("LI", 20, 20));
        f11_442.add(new CoordenadaDTO("EXT", 90, 45)); // Medios
        f11_442.add(new CoordenadaDTO("MC", 65, 35));
        f11_442.add(new CoordenadaDTO("MC", 35, 35));
        f11_442.add(new CoordenadaDTO("EXT", 10, 45));
        f11_442.add(new CoordenadaDTO("DEL", 55, 60)); // Delanteros
        f11_442.add(new CoordenadaDTO("DEL", 45, 60));
        catalogo.put("FUTBOL_11_4-4-2", f11_442);

        // --- FÚTBOL 11: 4-3-3 ---
        List<CoordenadaDTO> f11_433 = new ArrayList<>();
        f11_433.add(new CoordenadaDTO("POR", 50, 5));
        f11_433.add(new CoordenadaDTO("LD", 85, 20));
        f11_433.add(new CoordenadaDTO("CT", 60, 15));
        f11_433.add(new CoordenadaDTO("CT", 40, 15));
        f11_433.add(new CoordenadaDTO("LI", 15, 20));
        f11_433.add(new CoordenadaDTO("MCD", 50, 30));
        f11_433.add(new CoordenadaDTO("MC", 70, 40));
        f11_433.add(new CoordenadaDTO("MC", 30, 40));
        f11_433.add(new CoordenadaDTO("ED", 85, 60));
        f11_433.add(new CoordenadaDTO("EI", 15, 60));
        f11_433.add(new CoordenadaDTO("DC", 50, 65));
        catalogo.put("FUTBOL_11_4-3-3", f11_433);

        // --- FÚTBOL 7: 2-3-1 ---
        List<CoordenadaDTO> f7_231 = new ArrayList<>();
        f7_231.add(new CoordenadaDTO("POR", 50, 5));
        f7_231.add(new CoordenadaDTO("DEF", 30, 20));
        f7_231.add(new CoordenadaDTO("DEF", 70, 20));
        f7_231.add(new CoordenadaDTO("MED", 50, 35));
        f7_231.add(new CoordenadaDTO("EXT", 15, 45));
        f7_231.add(new CoordenadaDTO("EXT", 85, 45));
        f7_231.add(new CoordenadaDTO("DEL", 50, 60));
        catalogo.put("FUTBOL_7_2-3-1", f7_231);
    }

    public List<CoordenadaDTO> obtenerFormacion(String tipoFutbol, String nombreFormacion) {
        // Clave ej: "FUTBOL_11" + "_" + "4-4-2" = "FUTBOL_11_4-4-2"
        String clave = tipoFutbol + "_" + nombreFormacion;
        return catalogo.getOrDefault(clave, new ArrayList<>());
    }
    
    public List<String> listarDisponibles() {
        return new ArrayList<>(catalogo.keySet());
    }
}