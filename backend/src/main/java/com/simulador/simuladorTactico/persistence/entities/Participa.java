package com.simulador.simuladorTactico.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participa")
@Getter
@Setter
@NoArgsConstructor
public class Participa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	@ManyToOne
	@JoinColumn(name = "id_jugador", nullable = false)
	private Jugador jugador;

	
	@ManyToOne
	@JoinColumn(name = "id_alineacion", nullable = false)
	@JsonIgnore
	private Alineacion alineacion;

	@Column(name = "pos_x")
	private Integer posX;

	@Column(name = "pos_y")
	private Integer posY;
}