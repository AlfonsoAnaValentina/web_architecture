import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationService {

  constructor(private http: HttpClient) { }

  getCountries(): Observable<any> {
    return this.http.get(`http://localhost:8080/countries`);
  }
  getProvinces(): Observable<any> {
    return this.http.get(`http://localhost:8080/provinces`);
  }

  getProvincesByCountry(id): Observable<any> {
    return this.http.get(`http://localhost:8080/provinces`);
  }

  getCities(): Observable<any> {
    return this.http.get(`http://localhost:8080/cities`);
  }

  getCitiesByProvince(id): Observable<any> {
    return this.http.get(`http://localhost:8080/cities/province?provinceId=${id}`);
  }
}
