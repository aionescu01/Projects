import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Driver } from '../interfaces/driver';

@Injectable({
  providedIn: 'root'
})
export class DriversService {
  public url = 'https://localhost:44303/api/Driver';

  constructor(
    private http: HttpClient
  ) { }

  public getDrivers(): Observable<Driver[]> {
    return this.http.get<any>(this.url);
  }

  public getDriverById(id): Observable<Driver> {
    return this.http.get<any>(`${this.url}/get-by-number/${id}`);
  }

  public createDriver(driver: any): Observable<any> {
    return this.http.post(`${this.url}`, driver);
  }

  public editDriver(driver: any): Observable<any> {
    return this.http.put(`${this.url}`, driver);
  }

  public deleteDriver(driver: any): Observable<any> {
    return this.http.delete(`${this.url}/delete-by-id/${driver.number}`);
  }

}
