import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SentService {

  constructor(private http: HttpClient) { }
  getSentMessages(mail, page): Observable<any> {
    return this.http.get(`http://localhost:8080/message/sent?userMail=${mail}&page=${page}`);
  }
}
