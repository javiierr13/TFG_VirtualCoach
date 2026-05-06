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

	// Relación: Qué jugador está en esta posición
	@ManyToOne
	@JoinColumn(name = "id_jugador", nullable = false)
	private Jugador jugador;

	// Relación: A qué pizarra pertenece esta ficha
	@ManyToOne
	@JoinColumn(name = "id_alineacion", nullable = false)
	@JsonIgnore // <--- Corta el bucle. No queremos ver la alineación otra vez dentro de sí
				// misma.
	private Alineacion alineacion;

	@Column(name = "pos_x")
	private Integer posX;

	@Column(name = "pos_y")
	private Integer posY;
}