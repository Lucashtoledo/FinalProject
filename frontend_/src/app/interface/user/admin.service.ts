
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AdminInterface } from './admin-interface';
import { Observable } from 'rxjs';
import { ClientInterface } from './client-interface';
import { FormInterface } from '../form/form-interface';
import { ProcessInterface } from '../process/process-interface';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  apiURL = 'http://localhost:8080/admin';

  constructor(private http: HttpClient) { }

  public getAll(): Observable<AdminInterface[]>{
    return this.http.get<AdminInterface[]>(this.apiURL);
  }

  //Busca todos os clientes
  public getAllClient(): Observable<ClientInterface[]>{
    return this.http.get<ClientInterface[]>(this.apiURL + '/client');
  }

  public save(newAdmin: AdminInterface): Observable<AdminInterface>{
    return this.http.post<AdminInterface>(this.apiURL, newAdmin + '/newadmin/');
  }

  public put(updateAdmin: AdminInterface): Observable<AdminInterface>{
    return this.http.put<AdminInterface>(this.apiURL + '/' + updateAdmin.id, updateAdmin);
  }

  // Deleta o cliente
  public deleteClient(deleteClient: ClientInterface){
    return this.http.delete(this.apiURL + '/admin/' + deleteClient.id);
  }

  //Salva um novo cliente
  public saveClient(newClient: ClientInterface): Observable<ClientInterface>{
    return this.http.post<ClientInterface>(this.apiURL + '/newclient', newClient);
  }

  //Busca um cliente pelo CPF
  public findClientByCpf(cpf: string): Observable<ClientInterface> {
    return this.http.get<ClientInterface>(this.apiURL + '/client/' + cpf);
  }

  //Cria um novo formulário
  public saveForm(newForm: FormInterface): Observable<FormInterface> {
    return this.http.post<FormInterface>(this.apiURL + '/forms', newForm);
  }

  public saveProcess(newProcess: ProcessInterface): Observable<ProcessInterface> {
    return this.http.post<ProcessInterface>(this.apiURL + '/newprocess', newProcess);
  }

}
