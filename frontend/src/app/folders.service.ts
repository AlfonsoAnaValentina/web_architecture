import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FoldersService {
  url: '';
  constructor(private http: HttpClient) { }

  getAllFolders(id): Observable<any> {
    return this.http.get(`http://localhost:8080/message/labels?userId=${id}`);
  }

  createNewFolder(folder): Observable<any> {
    console.log('folder service');
    return this.http.post(`http://localhost:8080/message/label`, folder);
  }

  addMailToLabel(labeledMail): Observable<any> {
    return this.http.post('http://localhost:8080/message/mail-label', labeledMail);
  }
}
