import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { 
  IonContent, IonHeader, IonToolbar, IonTitle, IonList, 
  IonItem, IonLabel, IonAvatar, IonBadge, IonFab, 
  IonFabButton, IonIcon, IonModal, IonButton, IonButtons,
  IonInput, IonSelect, IonSelectOption, IonSearchbar, ModalController,
  IonItemSliding, IonItemOptions, IonItemOption, IonChip
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { 
  addOutline, trashOutline, searchOutline, 
  filterOutline, shirtOutline, bodyOutline,
  pencilOutline, closeOutline
} from 'ionicons/icons';
import { JugadorService } from '../../../core/services/jugador.service';
import { Jugador, Posicion, Pierna } from '../../../models/jugador.model';

@Component({
  selector: 'app-plantilla',
  templateUrl: './plantilla.page.html',
  standalone: true,
  imports: [
    CommonModule, FormsModule,
    IonContent, IonHeader, IonToolbar, IonTitle, IonList, 
    IonItem, IonLabel, IonAvatar, IonBadge, IonFab, 
    IonFabButton, IonIcon, IonModal, IonButton, IonButtons,
    IonInput, IonSelect, IonSelectOption, IonSearchbar, IonItemSliding,
    IonItemOptions, IonItemOption, IonChip
  ]
})
export class PlantillaPage implements OnInit {
  public jugadorService = inject(JugadorService);
  private modalCtrl = inject(ModalController);

  isModalOpen = signal(false);
  
  
  editandoJugadorId = signal<number | null>(null);

  
  nuevoJugador = signal({
    nombre: '',
    dorsal: null as number | null,
    posicion: 'PORTERO' as Posicion,
    pierna: 'DIESTRO' as Pierna
  });

  constructor() {
    addIcons({ 
      addOutline, trashOutline, searchOutline, 
      filterOutline, shirtOutline, bodyOutline,
      pencilOutline, closeOutline
    });
  }

  ngOnInit() {
    this.jugadorService.loadJugadores().subscribe();
  }

  setOpen(isOpen: boolean, jugador?: Jugador) {
    this.isModalOpen.set(isOpen);
    
    if (isOpen && jugador) {
      this.editandoJugadorId.set(jugador.id);
      this.nuevoJugador.set({
        nombre: jugador.nombre,
        dorsal: jugador.dorsal,
        posicion: jugador.posicion,
        pierna: jugador.piernaDominante
      });
    } else if (!isOpen) {
      this.editandoJugadorId.set(null);
      this.nuevoJugador.set({
        nombre: '',
        dorsal: null,
        posicion: 'PORTERO',
        pierna: 'DIESTRO'
      });
    }
  }

  onSearch(event: any) {
    const query = event.target.value.toLowerCase();
    if (query && query.trim() !== '') {
      this.jugadorService.buscarPorNombre(query).subscribe();
    } else {
      this.jugadorService.loadJugadores().subscribe();
    }
  }

  filterByPosicion(pos: Posicion | 'TODOS') {
    if (pos === 'TODOS') {
      this.jugadorService.loadJugadores().subscribe();
    } else {
      this.jugadorService.buscarPorPosicion(pos).subscribe();
    }
  }

  guardarJugador() {
    const { nombre, dorsal, posicion, pierna } = this.nuevoJugador();
    if (!nombre || !dorsal) return;

    const request = {
      nombre,
      dorsal: dorsal as number,
      posicion,
      pierna
    };

    if (this.editandoJugadorId()) {
      this.jugadorService.actualizarJugador(this.editandoJugadorId()!, request).subscribe(() => {
        this.setOpen(false);
      });
    } else {
      this.jugadorService.crearJugador(request).subscribe(() => {
        this.setOpen(false);
      });
    }
  }

  eliminar(id: number) {
    this.jugadorService.eliminarJugador(id).subscribe();
  }

  getPosicionColor(pos: string): string {
    switch(pos) {
      case 'PORTERO': return 'warning';
      case 'DEFENSA': return 'success';
      case 'MEDIOCENTRO': return 'primary';
      case 'DELANTERO': return 'danger';
      default: return 'medium';
    }
  }
}
