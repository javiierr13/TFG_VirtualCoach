import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { 
  IonContent, IonList, 
  IonItem, IonLabel, IonIcon, 
  IonButton,
  IonBadge,
  IonFab, IonFabButton, ToastController
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
    IonBadge,
    IonFab, IonFabButton
  ]
})
export class AlineacionesPage implements OnInit {
  public alineacionService = inject(AlineacionService);
  private router = inject(Router);
  private toastCtrl = inject(ToastController);

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
    this.alineacionService.borrarAlineacion(id).subscribe({
      next: async () => {
        const toast = await this.toastCtrl.create({
          message: 'ALINEACIÓN ELIMINADA CON ÉXITO',
          duration: 2000,
          color: 'success',
          position: 'top',
          mode: 'ios'
        });
        toast.present();
      },
      error: async (err) => {
        const mensaje = err?.error?.message || 'Error al eliminar la alineación.';
        const toast = await this.toastCtrl.create({
          message: mensaje,
          duration: 3000,
          color: 'danger',
          position: 'top',
          mode: 'ios'
        });
        toast.present();
      }
    });
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
