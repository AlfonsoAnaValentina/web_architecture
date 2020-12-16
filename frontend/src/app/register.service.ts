import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor( private http: HttpClient ) { }

  setUser(newUsr): Observable<any> {
    return this.http.post(`http://localhost:8080/user`, newUsr);
  }
}
