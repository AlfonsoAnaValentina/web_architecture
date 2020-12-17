import { Injectable } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { AuthService } from './auth.service';
@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  constructor(public router: Router) {}
  canActivate(): boolean {
    
    // Todo: validar que el token sea valido realmente
    if (localStorage.getItem('token'))
      return true;
    else{
      this.router.navigate(['login']);
      return false;
    }
  }
}