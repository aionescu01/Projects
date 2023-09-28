import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Car } from '../interfaces/car';

@Injectable({
  providedIn: 'root'
})
export class CarsService {
    public url = 'https://localhost:44303/api/Car';

  constructor(
    private http: HttpClient
  ) { }

  public getCars(): Observable<Car[]> {
    return this.http.get<any>(this.url);
  }

  public getCarById(id): Observable<Car> {
    return this.http.get<any>(`${this.url}/get-by-number/${id}`);
  }

  public createCar(car: any): Observable<any> {
    return this.http.post(`${this.url}`, car);
  }

  public editCar(car: any): Observable<any> {
    return this.http.put(`${this.url}`, car);
  }

  public deleteCar(car: any): Observable<any> {
    return this.http.delete(`${this.url}/delete-by-id/${car.number}`);
  }

}

