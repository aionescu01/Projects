import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sponsor } from '../interfaces/sponsor';

@Injectable({
  providedIn: 'root'
})
export class SponsorsService {
  public url = 'https://localhost:44303/api/Sponsor';

  constructor(
    private http: HttpClient
  ) { }

  public getSponsors(): Observable<Sponsor[]> {
    return this.http.get<any>(this.url);
  }

  public getSponsorById(id): Observable<Sponsor> {
    return this.http.get<any>(`${this.url}/get-by-id/${id}`);
  }

  public createSponsor(sponsor: any): Observable<any> {
    return this.http.post(`${this.url}`, sponsor);
  }

  public editSponsor(sponsor: any): Observable<any> {
    return this.http.put(`${this.url}`, sponsor);
  }

  public deleteSponsor(sponsor: any): Observable<any> {
    return this.http.delete(`${this.url}/delete-by-id/${sponsor.id}`);
  }

}

