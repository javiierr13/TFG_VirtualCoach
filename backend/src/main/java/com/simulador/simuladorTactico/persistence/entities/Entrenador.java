package com.simulador.simuladorTactico.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "entrenador")
@Getter
@Setter
@NoArgsConstructor
public class Entrenador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Column(nullable = false, unique = true, length = 150)
	private String correo;

	@Column(nullable = false)
	@JsonIgnore // SEGURIDAD: Evita que la contraseña viaje en la respuesta JSON
	private String contrasena;

	// Rol del usuario necesario para Spring Security
	private String rol;

	// Relación: Un entrenador tiene muchos jugadores
	@OneToMany(mappedBy = "entrenador", cascade = CascadeType.ALL)
	@JsonIgnore // EVITA BUCLE INFINITO
	private List<Jugador> jugadores;

	// Relación: Un entrenador tiene muchas alineaciones
	@OneToMany(mappedBy = "entrenador", cascade = CascadeType.ALL)
	@JsonIgnore // EVITA BUCLE INFINITO
	private List<Alineacion> alineaciones;
}