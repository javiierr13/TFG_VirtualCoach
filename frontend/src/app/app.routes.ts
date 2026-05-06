import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full',
  },
  {
    path: 'auth',
    children: [
      {
        path: 'login',
        loadComponent: () => import('./pages/auth/login/login.page').then(m => m.LoginPage),
      },
      {
        path: 'register',
        loadComponent: () => import('./pages/auth/register/register.page').then(m => m.RegisterPage),
      }
    ]
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./pages/dashboard/dashboard.page').then(m => m.DashboardPage),
    canActivate: [authGuard],
    children: [
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
      },
      {
        path: 'home',
        loadComponent: () => import('./pages/dashboard/home/home.page').then(m => m.HomePage),
      },
      {
        path: 'plantilla',
        loadComponent: () => import('./pages/dashboard/plantilla/plantilla.page').then(m => m.PlantillaPage),
      },
      {
        path: 'pizarra',
        loadComponent: () => import('./pages/dashboard/pizarra/pizarra.page').then(m => m.PizarraPage),
      },
      {
        path: 'alineaciones',
        loadComponent: () => import('./pages/dashboard/alineaciones/alineaciones.page').then(m => m.AlineacionesPage),
      },
      {
        path: 'perfil',
        loadComponent: () => import('./pages/dashboard/perfil/perfil.page').then(m => m.PerfilPage),
      }
    ]
  },
];
