import { ClientInterface } from './../../interface/user/client-interface';
import { Component, inject, Output } from '@angular/core';

import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatCardModule } from '@angular/material/card';
import { AdminService } from '../../interface/user/admin.service';


@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styles: `
    .full-width {
      width: 100%;
    }

    .shipping-card {
      min-width: 120px;
      margin: 20px auto;
    }

    .mat-radio-button {
      display: block;
      margin: 5px 0;
    }

    .row {
      display: flex;
      flex-direction: row;
    }

    .col {
      flex: 1;
      margin-right: 20px;
    }

    .col:last-child {
      margin-right: 0;
    }

  `,
  standalone: true,
  imports: [
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatRadioModule,
    MatCardModule,
    ReactiveFormsModule
  ]
})
export class AddClientComponent {

  constructor(private adminService: AdminService){};

  private fb = inject(FormBuilder);
  clientForm = this.fb.group({
    name: ['', Validators.required],
    cpf: ['', Validators.required],
    email: ['', Validators.required],
    phone: ['', Validators.required],
    password: ['', Validators.required],
    numberProcess: [null, Validators.nullValidator]
  });

  hasUnitNumber = false;

  onSubmit(){
    const newClient = this.clientForm.value as ClientInterface;
    this.salvarDado(newClient);
  }

  async salvarDado(client: ClientInterface) {
    this.adminService.findClientByCpf(client.cpf).subscribe(
      (existingClient) => {
        if (existingClient) {
          console.log('CPF já cadastrado');
        } else {
          this.adminService.saveClient(client).subscribe(
            () => console.log('Usuário ' + client.name + ' criado'),
            (error) => console.error('Erro ao salvar cliente:', error)
          );
        }
      },
      (error) => {
        if (error.status === 404) {
          // Se o cliente não for encontrado (404), você pode salvar o novo cliente
          this.adminService.saveClient(client).subscribe(
            () => console.log('Usuário ' + client.name + ' criado'),
            (saveError) => console.error('Erro ao salvar cliente:', saveError)
          );
        } else {
          console.error('Erro ao buscar cliente:', error);
        }
      }
    );
  }
}
