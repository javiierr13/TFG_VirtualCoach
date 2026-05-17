package com.simulador.simuladorTactico.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.simulador.simuladorTactico.persistence.enums.TipoFutbol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alineacion")
@Getter
@Setter
@NoArgsConstructor
public class Alineacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre", length = 100)
	private String nombre;

	@Column(name = "tipo_formacion", nullable = false, length = 200)
	private String tipoFormacion;

	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_futbol")
	private TipoFutbol tipoFutbol;

	
	@ManyToOne
	@JoinColumn(name = "id_entrenador", nullable = false)
	@JsonIgnore
	private Entrenador entrenador;

	
	@OneToMany(mappedBy = "alineacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Participa> posiciones;

	@PrePersist
	protected void onCreate() {
		if (this.fechaCreacion == null) {
			this.fechaCreacion = LocalDateTime.now();
		}
	}
}