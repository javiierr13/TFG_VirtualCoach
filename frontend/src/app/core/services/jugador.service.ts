import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Jugador, JugadorRequest, Posicion } from '../../models/jugador.model';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JugadorService {
  private http = inject(HttpClient);

  // Signal para la lista de jugadores
  jugadores = signal<Jugador[]>([]);

  loadJugadores(): Observable<Jugador[]> {
    return this.http.get<Jugador[]>('/api/jugadores').pipe(
      tap(data => this.jugadores.set(data))
    );
  }

  crearJugador(request: JugadorRequest): Observable<Jugador> {
    return this.http.post<Jugador>('/api/jugadores', request).pipe(
      tap(() => this.loadJugadores().subscribe())
    );
  }

  eliminarJugador(id: number): Observable<void> {
    return this.http.delete<void>(`/api/jugadores/${id}`).pipe(
      tap(() => this.loadJugadores().subscribe())
    );
  }

  actualizarJugador(id: number, request: JugadorRequest): Observable<Jugador> {
    return this.http.put<Jugador>(`/api/jugadores/${id}`, request).pipe(
      tap(() => this.loadJugadores().subscribe())
    );
  }

  buscarPorNombre(nombre: string): Observable<Jugador[]> {
    return this.http.get<Jugador[]>(`/api/jugadores/buscar-nombre?nombre=${nombre}`).pipe(
      tap(data => this.jugadores.set(data))
    );
  }

  buscarPorPosicion(posicion: Posicion): Observable<Jugador[]> {
    return this.http.get<Jugador[]>(`/api/jugadores/buscar-posicion?posicion=${posicion}`).pipe(
      tap(data => this.jugadores.set(data))
    );
  }
}
