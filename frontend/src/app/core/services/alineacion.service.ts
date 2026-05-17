import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Alineacion, AlineacionRequest } from '../../models/alineacion.model';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlineacionService {
  private http = inject(HttpClient);

  
  alineaciones = signal<Alineacion[]>([]);

  listarAlineaciones(): Observable<Alineacion[]> {
    return this.http.get<Alineacion[]>('/api/alineaciones').pipe(
      tap(data => this.alineaciones.set(data))
    );
  }

  guardarAlineacion(request: AlineacionRequest): Observable<Alineacion> {
    return this.http.post<Alineacion>('/api/alineaciones', request).pipe(
      tap(() => this.listarAlineaciones().subscribe())
    );
  }

  borrarAlineacion(id: number): Observable<void> {
    return this.http.delete<void>(`/api/alineaciones/${id}`).pipe(
      tap(() => this.listarAlineaciones().subscribe())
    );
  }

  getAlineacion(id: number): Observable<Alineacion> {
    return this.http.get<Alineacion>(`/api/alineaciones/${id}`);
  }

  actualizarAlineacion(id: number, request: AlineacionRequest): Observable<Alineacion> {
    return this.http.put<Alineacion>(`/api/alineaciones/${id}`, request).pipe(
      tap(() => this.listarAlineaciones().subscribe())
    );
  }
}
