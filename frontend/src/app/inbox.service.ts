import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InboxService {

  constructor(private http: HttpClient) { }
  getInboxMessages(mail): Observable<any> {
    return this.http.get(`http://localhost:8080/message/received?userMail=${mail}`);
  }
}
