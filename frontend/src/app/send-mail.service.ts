import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SendMailService {

  constructor(private http: HttpClient) { }

  sendMail(fromAdrs, toAdr, subject, message): Observable<any> {
    const newMail = {
      "fromAddress": fromAdrs,
      "message": message,
      "subject": subject,
      "toAddress": toAdr
    }

    return this.http.post('http://localhost:8080/message', newMail);
  }

  deleteEmail(id): Observable<any> {
    return this.http.delete(`http://localhost:8080/message/${id}`);
  }

  deleteMultipleEmail(ids): Observable<any> {
    return this.http.delete(`http://localhost:8080/message?ids=` + ids );
  }

  getMail(id): Observable<any> {
    return this.http.get(`http://localhost:8080/message/${id}`);
  }
}
