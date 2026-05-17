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
	@JsonIgnore
	private String contrasena;

	private String rol;

	
	@OneToMany(mappedBy = "entrenador", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Jugador> jugadores;

	
	@OneToMany(mappedBy = "entrenador", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Alineacion> alineaciones;
}