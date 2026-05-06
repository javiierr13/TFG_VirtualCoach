import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { 
  IonContent, IonHeader, IonToolbar, IonTitle, 
  IonGrid, IonRow, IonCol, IonCard, IonCardHeader, 
  IonCardSubtitle, IonCardTitle, IonCardContent, IonIcon 
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { peopleOutline, gridOutline, listOutline, trendingUpOutline } from 'ionicons/icons';
import { JugadorService } from '../../../core/services/jugador.service';
import { AlineacionService } from '../../../core/services/alineacion.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  standalone: true,
  imports: [
    CommonModule, RouterModule,
    IonContent, IonHeader, IonToolbar, IonTitle, 
    IonGrid, IonRow, IonCol, IonCard, IonCardHeader, 
    IonCardSubtitle, IonCardTitle, IonCardContent, IonIcon
  ]
})
export class HomePage implements OnInit {
  public jugadorService = inject(JugadorService);
  public alineacionService = inject(AlineacionService);

  constructor() {
    addIcons({ peopleOutline, gridOutline, listOutline, trendingUpOutline });
  }

  ngOnInit() {
    this.jugadorService.loadJugadores().subscribe();
    this.alineacionService.listarAlineaciones().subscribe();
  }
}
