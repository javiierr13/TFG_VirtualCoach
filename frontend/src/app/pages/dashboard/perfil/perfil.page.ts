import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { 
  IonContent, IonHeader, IonToolbar, IonTitle,
  IonList, IonItem, IonLabel, IonInput, IonButton,
  IonIcon, IonButtons, IonBackButton, IonCard,
  IonCardHeader, IonCardTitle, IonCardContent,
  ToastController
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { personOutline, mailOutline, saveOutline, arrowBackOutline } from 'ionicons/icons';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.page.html',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule,
    IonContent, IonHeader, IonToolbar, IonTitle, 
    IonList, IonItem, IonLabel, IonInput, IonButton,
    IonIcon, IonButtons, IonBackButton, IonCard,
    IonCardHeader, IonCardTitle, IonCardContent
  ]
})
export class PerfilPage implements OnInit {
  private fb = inject(FormBuilder);
  public authService = inject(AuthService);
  private toastCtrl = inject(ToastController);

  perfilForm: FormGroup;

  constructor() {
    addIcons({ personOutline, mailOutline, saveOutline, arrowBackOutline });
    this.perfilForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      correo: ['', [Validators.required, Validators.email]]
    });
  }

  ngOnInit() {
    // Inicializamos con los datos reales almacenados en el servicio
    this.perfilForm.patchValue({
      nombre: this.authService.nombreEntrenador(),
      correo: this.authService.emailUsuario()
    });
  }

  async guardarCambios() {
    if (this.perfilForm.valid) {
      const toast = await this.toastCtrl.create({
        message: '¡PERFIL GUARDADO CON ÉXITO!',
        duration: 3000,
        position: 'top',
        color: 'success',
        cssClass: 'premium-toast',
        buttons: [
          {
            icon: 'checkmark-circle',
            side: 'start'
          }
        ]
      });
      
      await toast.present();
    }
  }
}
