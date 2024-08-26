import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ClientInterface } from '../interface/user/client-interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiURL = 'http://localhost:8080/access';

  constructor(private http: HttpClient) {}

  login(email: string, password: string, role: string): Observable<boolean> {
    const params = new HttpParams()
      .set('email', email)
      .set('password', password)
      .set('role', role);

    return this.http.get<boolean>(this.apiURL + '/login', { params });
  }
  getClient(email: string): Observable<ClientInterface> {
    const params = new HttpParams().set('email', email);
    return this.http.get<ClientInterface>(this.apiURL+'/client', { params });
  }
}
