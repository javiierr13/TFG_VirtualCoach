import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { 
  IonContent, IonList, 
  IonItem, IonLabel, IonIcon, 
  IonButton,
  IonBadge, IonItemSliding, IonItemOptions, IonItemOption,
  IonFab, IonFabButton
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { trashOutline, calendarOutline, footballOutline, closeOutline, addOutline } from 'ionicons/icons';
import { AlineacionService } from '../../../core/services/alineacion.service';

@Component({
  selector: 'app-alineaciones',
  templateUrl: './alineaciones.page.html',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    IonContent, IonList, 
    IonItem, IonLabel, IonIcon, 
    IonButton,
    IonBadge, IonItemSliding, IonItemOptions, IonItemOption,
    IonFab, IonFabButton
  ]
})
export class AlineacionesPage implements OnInit {
  public alineacionService = inject(AlineacionService);
  private router = inject(Router);

  constructor() {
    addIcons({ trashOutline, calendarOutline, footballOutline, closeOutline, addOutline });
  }

  ngOnInit() {
    this.alineacionService.listarAlineaciones().subscribe();
  }

  crearNuevaTactica() {
    this.router.navigate(['/dashboard/pizarra']);
  }

  eliminar(id: number) {
    this.alineacionService.borrarAlineacion(id).subscribe();
  }

  editarTactica(id: number) {
    this.router.navigate(['/dashboard/pizarra'], { queryParams: { id } });
  }

  formatDate(dateStr: string): string {
    const date = new Date(dateStr);
    return date.toLocaleDateString('es-ES', { 
      day: '2-digit', 
      month: 'short', 
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}
