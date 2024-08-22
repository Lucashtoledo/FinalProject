
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AdminInterface } from './admin-interface';
import { Observable } from 'rxjs';
import { ClientInterface } from './client-interface';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  apiURL = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) { }

  public getAll(): Observable<AdminInterface[]>{
    return this.http.get<AdminInterface[]>(this.apiURL);
  }

  public save(newAdmin: AdminInterface): Observable<AdminInterface>{
    return this.http.post<AdminInterface>(this.apiURL, newAdmin + '/newadmin/');
  }

  public put(updateAdmin: AdminInterface): Observable<AdminInterface>{
    return this.http.put<AdminInterface>(this.apiURL + '/' + updateAdmin.id, updateAdmin);
  }

  public delete(deleteAdmin: AdminInterface){
    return this.http.delete(this.apiURL + '/admin/' + deleteAdmin.id);
  }
  public saveClient(newClient: ClientInterface): Observable<ClientInterface>{
    return this.http.post<ClientInterface>(this.apiURL+ '/newclient', newClient);
  }

  public findClientByCpf(cpf: string): Observable<ClientInterface> {
    return this.http.get<ClientInterface>(this.apiURL + '/client/' + cpf);
  }

}
