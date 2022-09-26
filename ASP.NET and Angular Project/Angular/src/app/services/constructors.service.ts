import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Constructor } from '../interfaces/constructor';

@Injectable({
  providedIn: 'root'
})
export class ConstructorsService {
  public url = 'https://localhost:44303/api/Constructor';

  constructor(
    private http: HttpClient
  ) { }

  public getConstructors(): Observable<Constructor[]> {
    return this.http.get<any>(this.url);
  }

  public getConstructorById(Name): Observable<Constructor> {
    return this.http.get<any>(`${this.url}/get-by-name/${Name}`);
  }

  public createConstructor(constructor: any): Observable<any> {
    return this.http.post(`${this.url}`, constructor);
  }

  public editConstructor(constructor: any): Observable<any> {
    return this.http.put(`${this.url}`, constructor);
  }

  public deleteConstructor(constructor: any): Observable<any> {
    return this.http.delete(`${this.url}/delete-by-id/${constructor.Name}`);
  }

}

