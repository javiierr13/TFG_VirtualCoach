import { Injectable, inject, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

interface LoginResponse {
  accessToken: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private http = inject(HttpClient);
  private router = inject(Router);

  
  token = signal<string | null>(localStorage.getItem('token'));
  nombreEntrenador = signal<string | null>(localStorage.getItem('nombreEntrenador'));
  emailUsuario = signal<string | null>(localStorage.getItem('emailUsuario'));

  isAuthenticated = computed(() => !!this.token());

  login(correo: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>('/auth/login', { correo, password }).pipe(
      tap(res => {
        this.token.set(res.accessToken);
        localStorage.setItem('token', res.accessToken);
        
        
        this.emailUsuario.set(correo);
        localStorage.setItem('emailUsuario', correo);

        
        if (!this.nombreEntrenador()) {
          const defaultName = correo.split('@')[0];
          this.nombreEntrenador.set(defaultName);
          localStorage.setItem('nombreEntrenador', defaultName);
        }
      })
    );
  }

  register(nombre: string, correo: string, password1: string, password2: string): Observable<string> {
    return this.http.post('/auth/register', { nombre, correo, password1, password2 }, { responseType: 'text' }).pipe(
      tap(() => {
        this.nombreEntrenador.set(nombre);
        localStorage.setItem('nombreEntrenador', nombre);
        this.emailUsuario.set(correo);
        localStorage.setItem('emailUsuario', correo);
      })
    );
  }

  logout() {
    this.token.set(null);
    this.nombreEntrenador.set(null);
    this.emailUsuario.set(null);
    localStorage.removeItem('token');
    localStorage.removeItem('nombreEntrenador');
    localStorage.removeItem('emailUsuario');
    this.router.navigate(['/auth/login']);
  }
}
