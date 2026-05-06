export type Posicion = 'PORTERO' | 'DEFENSA' | 'MEDIOCENTRO' | 'DELANTERO';
export type Pierna = 'DIESTRO' | 'ZURDO' | 'AMBIDIESTRO';

export interface Jugador {
  id: number;
  nombre: string;
  dorsal: number;
  posicion: Posicion;
  piernaDominante: Pierna;
}

export interface JugadorRequest {
  nombre: string;
  dorsal: number;
  posicion: Posicion;
  pierna: Pierna;
}
