import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) {}

  getUser(usr, pass): Observable<any> {
    return this.http.get(`http://localhost:8080/user/login?password=${pass}&userMail=${usr}`);
  }

  getUser2(usr, pass): Observable<any> {
    const usrLog = {
      "password": pass,
      "username": usr
    };
    return this.http.post(`http://localhost:8080/authenticate`, usrLog);
  }

}