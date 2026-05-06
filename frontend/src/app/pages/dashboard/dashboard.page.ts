import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { 
  IonSplitPane, IonMenu, IonContent, IonList, 
  IonListHeader, IonNote, IonMenuToggle, IonItem, 
  IonIcon, IonLabel, IonRouterOutlet, IonHeader,
  IonToolbar, IonTitle, IonButtons, IonMenuButton,
  IonPopover
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { 
  homeOutline, peopleOutline, gridOutline, 
  listOutline, logOutOutline, personCircleOutline,
  settingsOutline
} from 'ionicons/icons';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.page.html',
  standalone: true,
  imports: [
    CommonModule, RouterModule,
    IonSplitPane, IonMenu, IonContent, IonList, 
    IonListHeader, IonNote, IonMenuToggle, IonItem, 
    IonIcon, IonLabel, IonRouterOutlet, IonHeader,
    IonToolbar, IonTitle, IonButtons, IonMenuButton,
    IonPopover
  ]
})
export class DashboardPage {
  public authService = inject(AuthService);
  private router = inject(Router);

  public appPages = [
    { title: 'Dashboard', url: '/dashboard/home', icon: 'home-outline' },
    { title: 'Plantilla', url: '/dashboard/plantilla', icon: 'people-outline' },
    { title: 'Mis Tácticas', url: '/dashboard/alineaciones', icon: 'list-outline' },
  ];

  constructor() {
    addIcons({ 
      homeOutline, peopleOutline, gridOutline, 
      listOutline, logOutOutline, personCircleOutline,
      settingsOutline
    });
  }

  logout() {
    this.authService.logout();
  }

  goToProfile() {
    this.router.navigate(['/dashboard/perfil']);
  }
}
