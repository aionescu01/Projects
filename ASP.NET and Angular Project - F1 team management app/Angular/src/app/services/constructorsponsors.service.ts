import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Constructorsponsor } from '../interfaces/constructorsponsor';

@Injectable({
  providedIn: 'root'
})
export class ConstructorsponsorsService {
  public url = 'https://localhost:44303/api/Constructorsponsors';

  constructor(
    private http: HttpClient
  ) { }

  public getConstructorsponsors(): Observable<Constructorsponsor[]> {
    return this.http.get<any>(this.url);
  }

  public getConstructorsponsorById(id): Observable<Constructorsponsor> {
    return this.http.get<any>(`${this.url}/get-by-id/${id}`);
  }

  public createConstructorsponsor(constructorsponsor: any): Observable<any> {
    return this.http.post(`${this.url}`, constructorsponsor);
  }

  public editConstructorsponsor(constructorsponsor: any): Observable<any> {
    return this.http.put(`${this.url}`, constructorsponsor);
  }

  public deleteConstructorsponsor(constructorsponsor: any): Observable<any> {
    return this.http.delete(`${this.url}/delete-by-id/${constructorsponsor.sponsorId}/${constructorsponsor.constructorName}`);
  }

}

