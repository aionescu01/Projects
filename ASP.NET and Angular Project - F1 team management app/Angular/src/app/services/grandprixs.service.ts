import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Grandprix } from '../interfaces/grandprix';

@Injectable({
  providedIn: 'root'
})
export class GrandprixsService {
  public url = 'https://localhost:44303/api/GrandPrix';

  constructor(
    private http: HttpClient
  ) { }

  public getGrandprixs(): Observable<Grandprix[]> {
    return this.http.get<any>(this.url);
  }

  public getGrandprixById(id): Observable<Grandprix> {
    return this.http.get<any>(`${this.url}/get-by-id/${id}`);
  }

  public createGrandprix(grandprix: any): Observable<any> {
    return this.http.post(`${this.url}`, grandprix);
  }

  public editGrandprix(grandprix: any): Observable<any> {
    return this.http.put(`${this.url}`, grandprix);
  }

  public deleteGrandprix(grandprix: any): Observable<any> {
    return this.http.delete(`${this.url}/delete-by-id/${grandprix.number}`);
  }

}

