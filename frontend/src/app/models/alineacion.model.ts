export type TipoFutbol = 'FUTBOL_11' | 'FUTBOL_7' | 'FUTBOL_SALA';

export interface PosicionJugadorDTO {
  jugadorId: number;
  posX: number;
  posY: number;
}

export interface AlineacionRequest {
  nombre: string;
  tipoFormacion: string;
  tipoFutbol: TipoFutbol;
  posiciones: PosicionJugadorDTO[];
}

export interface Participa {
  id: number;
  jugador: { 
    id: number; 
    nombre: string; 
    dorsal: number; 
    posicion: string 
  };
  posX: number;
  posY: number;
}

export interface Alineacion {
  id: number;
  nombre: string;
  tipoFormacion: string;
  tipoFutbol: TipoFutbol;
  fechaCreacion: string;
  posiciones: Participa[];
}
