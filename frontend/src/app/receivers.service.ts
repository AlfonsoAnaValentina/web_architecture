import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor( private http: HttpClient ) { }

  setReceiver(newReceiver): Observable<any> {
    return this.http.post(`http://localhost:8080/receiver`, newReceiver);
  }
  setMultipleReceivers(newReceivers): Observable<any> {
    return this.http.post(`http://localhost:8080/receiver/multiple`, newReceivers);
  }
  getReceipients(mail): Observable<any> {
    return this.http.post(`http://localhost:8080/receiver/receipient`, mail);
  }
  getCc(mail): Observable<any> {
    return this.http.post(`http://localhost:8080/receiver/cc`, mail);
  }
  getCco(mail): Observable<any> {
    return this.http.post(`http://localhost:8080/receiver/cco`, mail);
  }
}
