import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { ToastController } from '@ionic/angular/standalone';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const toastCtrl = inject(ToastController);

  return next(req).pipe(
    catchError((error) => {
      let message = 'Ha ocurrido un error inesperado';

      if (error.error) {
        // Si el error es un string (posible JSON stringificado por responseType:'text')
        if (typeof error.error === 'string') {
          try {
            const parsed = JSON.parse(error.error);
            message = parsed.message || parsed.error || message;
          } catch (e) {
            message = error.error;
          }
        } else if (error.error.message) {
          message = error.error.message;
        }
      }

      if (error.status === 401 || error.status === 403) {
        message = 'Sesión inválida o expirada. Por favor, identifícate de nuevo.';
        authService.logout();
      } else if (error.status === 404) {
        message = 'Error 404: No se encuentra el servidor (revisa el proxy).';
      }

      toastCtrl.create({
        message,
        duration: 5000,
        position: 'top',
        color: 'danger',
        cssClass: 'custom-toast'
      }).then(toast => toast.present());

      return throwError(() => error);
    })
  );
};
