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
}
