import { Component, inject, OnInit, signal, effect, ElementRef, ViewChild, computed } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {
  IonContent, IonButton,
  IonIcon, IonSelect, IonSelectOption, IonLabel,
  IonItem, ToastController, IonInput
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { saveOutline, refreshOutline, optionsOutline, personCircleOutline, arrowBackOutline, closeOutline, flagOutline, peopleOutline } from 'ionicons/icons';
import { JugadorService } from '../../../core/services/jugador.service';
import { AlineacionService } from '../../../core/services/alineacion.service';
import { TipoFutbol, PosicionJugadorDTO } from '../../../models/alineacion.model';
import { Jugador } from '../../../models/jugador.model';

interface PosicionRival {
  id: number;
  numero: number;
  posX: number;
  posY: number;
}

@Component({
  selector: 'app-pizarra',
  templateUrl: './pizarra.page.html',
  styleUrls: ['./pizarra.page.scss'],
  standalone: true,
  imports: [
    CommonModule, FormsModule, RouterModule,
    IonContent, IonButton,
    IonIcon, IonSelect, IonSelectOption, IonLabel,
    IonItem, IonInput
  ]
})
export class PizarraPage implements OnInit {
  private jugadorService = inject(JugadorService);
  private alineacionService = inject(AlineacionService);
  private toastCtrl = inject(ToastController);
  private route = inject(ActivatedRoute);
  
  mousePos = signal({ x: 0, y: 0 });

  alineacionId = signal<number | null>(null);
  cargando = signal(false);

  nombreTactica = signal<string>('');
  tipoFutbol = signal<TipoFutbol>('FUTBOL_11');
  formacion = signal<string>('4-4-2');

  formacionesDisponibles = {
    'FUTBOL_11': ['4-3-3', '3-5-2', '5-4-1'],
    'FUTBOL_7': ['2-3-1', '3-2-1', '3-1-2'],
    'FUTBOL_SALA': ['1-2-1', '2-2', '3-1']
  };

  coordenadasBase: any = {
    '4-4-2': [
      { x: 8, y: 50 }, // Portero
      { x: 25, y: 20 }, { x: 25, y: 40 }, { x: 25, y: 60 }, { x: 25, y: 80 }, // Defensas
      { x: 50, y: 20 }, { x: 50, y: 40 }, { x: 50, y: 60 }, { x: 50, y: 80 }, // Medios
      { x: 75, y: 35 }, { x: 75, y: 65 } // Delanteros
    ],
    '4-3-3': [
      { x: 8, y: 50 },
      { x: 25, y: 20 }, { x: 25, y: 40 }, { x: 25, y: 60 }, { x: 25, y: 80 },
      { x: 50, y: 25 }, { x: 50, y: 50 }, { x: 50, y: 75 },
      { x: 75, y: 20 }, { x: 75, y: 50 }, { x: 75, y: 80 }
    ],
    '3-5-2': [
      { x: 8, y: 50 },
      { x: 25, y: 25 }, { x: 25, y: 50 }, { x: 25, y: 75 },
      { x: 45, y: 20 }, { x: 55, y: 35 }, { x: 55, y: 50 }, { x: 55, y: 65 }, { x: 45, y: 80 },
      { x: 75, y: 40 }, { x: 75, y: 60 }
    ],
    '5-4-1': [
      { x: 8, y: 50 },
      { x: 25, y: 15 }, { x: 25, y: 33 }, { x: 25, y: 50 }, { x: 25, y: 67 }, { x: 25, y: 85 },
      { x: 50, y: 20 }, { x: 50, y: 40 }, { x: 50, y: 60 }, { x: 50, y: 80 },
      { x: 75, y: 50 }
    ],
    '2-3-1': [ // Futbol 7
      { x: 8, y: 50 },
      { x: 25, y: 30 }, { x: 25, y: 70 },
      { x: 50, y: 20 }, { x: 50, y: 50 }, { x: 50, y: 80 },
      { x: 75, y: 50 }
    ],
    '3-2-1': [ // Futbol 7
      { x: 8, y: 50 },
      { x: 25, y: 20 }, { x: 25, y: 50 }, { x: 25, y: 80 },
      { x: 50, y: 35 }, { x: 50, y: 65 },
      { x: 75, y: 50 }
    ],
    '3-1-2': [ // Futbol 7
      { x: 8, y: 50 },
      { x: 25, y: 20 }, { x: 25, y: 50 }, { x: 25, y: 80 },
      { x: 50, y: 50 },
      { x: 75, y: 35 }, { x: 75, y: 65 }
    ],
    '1-2-1': [ // Sala
      { x: 10, y: 50 },
      { x: 30, y: 50 },
      { x: 50, y: 25 }, { x: 50, y: 75 },
      { x: 75, y: 50 }
    ],
    '2-2': [ // Sala
      { x: 10, y: 50 },
      { x: 30, y: 30 }, { x: 30, y: 70 },
      { x: 65, y: 30 }, { x: 65, y: 70 }
    ],
    '3-1': [ // Sala
      { x: 10, y: 50 },
      { x: 30, y: 20 }, { x: 30, y: 50 }, { x: 30, y: 80 },
      { x: 70, y: 50 }
    ]
  };

  // Lista de jugadores colocados en la pizarra
  posiciones = signal<PosicionJugadorDTO[]>([]);
  posicionesRivales = signal<PosicionRival[]>([]);

  // Jugadores que no están en el campo
  banquillo = computed(() => {
    const todos = this.jugadorService.jugadores();
    const puestos = this.posiciones().map(p => p.jugadorId);
    return todos.filter(j => !puestos.includes(j.id));
  });

  constructor() {
    addIcons({ saveOutline, refreshOutline, optionsOutline, personCircleOutline, arrowBackOutline, closeOutline, flagOutline, peopleOutline });

    // Al cambiar la formación o el tipo de fútbol, verificamos validez pero NO plantamos automático
    effect(() => {
      const tipo = this.tipoFutbol();
      const form = this.formacion();

      const validas = (this.formacionesDisponibles as any)[tipo];
      if (!validas.includes(form)) {
        this.formacion.set(validas[0]);
      }
    }, { allowSignalWrites: true });
  }

  ngOnInit() {}

  ionViewWillEnter() {
    this.jugadorService.loadJugadores().subscribe(() => {
      const id = this.route.snapshot.queryParamMap.get('id');
      if (id) {
        this.alineacionId.set(+id);
        this.cargarTactica(+id);
      } else {
        this.alineacionId.set(null);
        this.nombreTactica.set('');
        this.posiciones.set([]);
        this.posicionesRivales.set([]);
      }
    });
  }

  cargarTactica(id: number) {
    this.cargando.set(true);
    this.alineacionService.getAlineacion(id).subscribe({
      next: (ali) => {
        this.posicionesRivales.set([]); // Limpiamos rivales al cargar para que no se queden en memoria
        this.nombreTactica.set(ali.nombre || '');
        this.tipoFutbol.set(ali.tipoFutbol);
        this.formacion.set(ali.tipoFormacion);

        // Mapear Participa a PosicionJugadorDTO
        const posDTOs: PosicionJugadorDTO[] = ali.posiciones.map(p => ({
          jugadorId: p.jugador.id,
          posX: p.posX,
          posY: p.posY
        }));

        this.posiciones.set(posDTOs);
        this.cargando.set(false);
      },
      error: () => this.cargando.set(false)
    });
  }

  plantarRival() {
    const coords = this.coordenadasBase[this.formacion()];
    if (!coords) return;

    const numRequerido = this.tipoFutbol() === 'FUTBOL_11' ? 11 : this.tipoFutbol() === 'FUTBOL_7' ? 7 : 5;
    const nuevasPosicionesRivales: PosicionRival[] = [];

    for (let i = 0; i < numRequerido; i++) {
      nuevasPosicionesRivales.push({
        id: 1000 + i,
        numero: i + 1,
        posX: 100 - coords[i].x,
        posY: coords[i].y
      });
    }
    this.posicionesRivales.set(nuevasPosicionesRivales);
  }

  limpiarCampo() {
    this.posiciones.set([]);
    this.posicionesRivales.set([]);
  }

  async resetPizarra() {
    // ... logic for manual full reset if needed ...
    const jugadores = this.jugadorService.jugadores();
    const numRequerido = this.tipoFutbol() === 'FUTBOL_11' ? 11 : this.tipoFutbol() === 'FUTBOL_7' ? 7 : 5;

    if (jugadores.length < numRequerido) {
      const toast = await this.toastCtrl.create({
        message: `Faltan jugadores en tu plantilla (necesitas ${numRequerido})`,
        duration: 3000,
        color: 'warning',
        position: 'top'
      });
      toast.present();
      return;
    }

    const nuevasPosiciones: PosicionJugadorDTO[] = [];
    const coords = this.coordenadasBase[this.formacion()];

    for (let i = 0; i < numRequerido; i++) {
      nuevasPosiciones.push({
        jugadorId: jugadores[i].id,
        posX: coords ? coords[i].x : 10 + (i * 15) % 80,
        posY: coords ? coords[i].y : 20 + Math.floor(i / 3) * 20
      });
    }

    this.posiciones.set(nuevasPosiciones);
    this.plantarRival();
  }

  draggedJugadorId: number | null = null;
  draggedRivalId: number | null = null;
  draggedBenchJugadorId: number | null = null;

  onDragStart(jugadorId: number) {
    this.draggedJugadorId = jugadorId;
  }

  onDragStartRival(rivalId: number) {
    this.draggedRivalId = rivalId;
  }

  onDragStartBench(jugadorId: number) {
    this.draggedBenchJugadorId = jugadorId;
  }

  onGlobalPointerMove(event: PointerEvent) {
    if (this.draggedBenchJugadorId === null && this.draggedJugadorId === null && this.draggedRivalId === null) return;
    this.mousePos.set({ x: event.clientX, y: event.clientY });
  }

  onDragMove(event: PointerEvent) {
    if (this.draggedJugadorId === null && this.draggedRivalId === null) return;

    const container = (event.currentTarget as HTMLElement);
    const rect = container.getBoundingClientRect();

    let x = ((event.clientX - rect.left) / rect.width) * 100;
    let y = ((event.clientY - rect.top) / rect.height) * 100;

    // Límites para que no se salgan del campo
    x = Math.max(5, Math.min(95, x));
    y = Math.max(5, Math.min(95, y));

    if (this.draggedJugadorId !== null) {
      this.posiciones.update(pos => pos.map(p =>
        p.jugadorId === this.draggedJugadorId ? { ...p, posX: Math.round(x), posY: Math.round(y) } : p
      ));
    } else if (this.draggedRivalId !== null) {
      this.posicionesRivales.update(pos => pos.map(p =>
        p.id === this.draggedRivalId ? { ...p, posX: Math.round(x), posY: Math.round(y) } : p
      ));
    }
  }

  onDragEnd(event?: PointerEvent) {
    if (this.draggedBenchJugadorId !== null && event) {
      const container = (event.currentTarget as HTMLElement);
      const rect = container.getBoundingClientRect();

      let x = ((event.clientX - rect.left) / rect.width) * 100;
      let y = ((event.clientY - rect.top) / rect.height) * 100;

      x = Math.max(5, Math.min(95, x));
      y = Math.max(5, Math.min(95, y));

      const nuevoPuesto: PosicionJugadorDTO = {
        jugadorId: this.draggedBenchJugadorId,
        posX: Math.round(x),
        posY: Math.round(y)
      };

      this.posiciones.update(pos => [...pos, nuevoPuesto]);
    }

    this.draggedJugadorId = null;
    this.draggedRivalId = null;
    this.draggedBenchJugadorId = null;
  }

  onDropToBench() {
    if (this.draggedJugadorId !== null) {
      // Si soltamos un jugador del campo en el banquillo, lo quitamos de la pizarra
      this.posiciones.update(pos => pos.filter(p => p.jugadorId !== this.draggedJugadorId));
      this.draggedJugadorId = null;
    }
  }

  eliminarJugador(jugadorId: number) {
    this.posiciones.update(pos => pos.filter(p => p.jugadorId !== jugadorId));
  }

  eliminarRival(rivalId: number) {
    this.posicionesRivales.update(pos => pos.filter(p => p.id !== rivalId));
  }

  getJugadorById(id: number | null): Jugador | undefined {
    if (id === null) return undefined;
    return this.jugadorService.jugadores().find(j => j.id === id);
  }

  getJugadorName(id: number | null): string {
    if (id === null) return 'Jugador';
    const j = this.getJugadorById(id);
    return j ? j.nombre.split(' ')[0] : 'Jugador';
  }

  async guardar() {
    if (this.posiciones().length === 0) return;

    const request = {
      nombre: this.nombreTactica() || `Táctica ${this.formacion()}`,
      tipoFormacion: this.formacion(),
      tipoFutbol: this.tipoFutbol(),
      posiciones: this.posiciones()
    };

    const observable = this.alineacionId()
      ? this.alineacionService.actualizarAlineacion(this.alineacionId()!, request)
      : this.alineacionService.guardarAlineacion(request);

    observable.subscribe({
      next: async (ali) => {
        if (!this.alineacionId()) {
          this.alineacionId.set(ali.id);
        }
        const toast = await this.toastCtrl.create({
          message: this.alineacionId() ? 'CAMBIOS GUARDADOS CON ÉXITO' : 'TÁCTICA GUARDADA CORRECTAMENTE',
          duration: 2000,
          color: 'success',
          cssClass: 'custom-toast'
        });
        toast.present();
      }
    });
  }
}
