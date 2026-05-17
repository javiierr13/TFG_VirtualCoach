package com.simulador.simuladorTactico.persistence.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simulador.simuladorTactico.persistence.enums.PiernaDominante;
import com.simulador.simuladorTactico.persistence.enums.Posicion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jugador")
@Getter
@Setter
@NoArgsConstructor
public class Jugador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String nombre;

	@Column(nullable = false)
	private Integer dorsal;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private Posicion posicion;

	@Enumerated(EnumType.STRING)
	@Column(name = "pierna_dominante")
	private PiernaDominante piernaDominante;

	
	@ManyToOne
	@JoinColumn(name = "id_entrenador", nullable = false)
	@JsonIgnore
	private Entrenador entrenador;

	
	@OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Participa> participaciones;

}