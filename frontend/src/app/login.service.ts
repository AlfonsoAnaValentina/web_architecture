import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {}
  //const configUrl = 'http://localhost:8080/user';

  getUser(usr, pass): Observable<any> {
    return this.http.get(`http://localhost:8080/user/login?password=${pass}&userMail=${usr}`);
  }

}