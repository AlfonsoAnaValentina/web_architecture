import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpClient, HttpRequest, HttpEvent } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class InboxService {

  constructor(private http: HttpClient) { }
  getInboxMessages(mail,page): Observable<any> {
    return this.http.get(`http://localhost:8080/message/received?userMail=${mail}&page=`+page);
  }

  setAsRead(id, body): Observable<any> {
    console.log('pasa por el servicio');
    return this.http.put(`http://localhost:8080/message/${id}`, body);
  }

  onUpload(selectedFile, id): Observable<any> {

    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', selectedFile, selectedFile.name);
    return this.http.post('http://localhost:8080/message/'+id+'/upload-image', uploadImageData, { observe: 'response' });
  }

}
