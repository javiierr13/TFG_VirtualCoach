import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { 
  IonContent, IonItem, 
  IonInput, IonButton, IonIcon,
  IonCard, IonCardHeader, IonCardTitle, IonCardContent,
  IonSpinner, ToastController
} from '@ionic/angular/standalone';
import { addIcons } from 'ionicons';
import { personOutline, mailOutline, lockClosedOutline, footballOutline, arrowBackOutline } from 'ionicons/icons';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, RouterModule,
    IonContent, IonItem, 
    IonInput, IonButton, IonIcon,
    IonCard, IonCardHeader, IonCardTitle, IonCardContent,
    IonSpinner
  ]
})
export class RegisterPage {
  private authService = inject(AuthService);
  private router = inject(Router);
  private toastCtrl = inject(ToastController);
  private fb = inject(FormBuilder);

  registerForm: FormGroup;
  loading = signal(false);

  constructor() {
    addIcons({ personOutline, mailOutline, lockClosedOutline, footballOutline, arrowBackOutline });
    
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      correo: ['', [Validators.required, Validators.email]],
      password1: ['', [Validators.required, Validators.minLength(6)]],
      password2: ['', [Validators.required]]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const p1 = control.get('password1')?.value;
    const p2 = control.get('password2')?.value;
    return p1 === p2 ? null : { passwordMismatch: true };
  }

  async onRegister() {
    if (this.registerForm.invalid) {
      this.registerForm.markAllAsTouched();
      return;
    }

    const { nombre, correo, password1, password2 } = this.registerForm.value;

    this.loading.set(true);
    this.authService.register(nombre, correo, password1, password2).subscribe({
      next: async () => {
        this.loading.set(false);
        const toast = await this.toastCtrl.create({
          message: '¡Bienvenido al club! Registro completado.',
          duration: 3000,
          color: 'success',
          position: 'top',
          mode: 'ios'
        });
        toast.present();
        this.router.navigate(['/auth/login']);
      },
      error: () => {
        this.loading.set(false);
      }
    });
  }
}
