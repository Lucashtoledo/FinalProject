import { ClientInterface } from './../../interface/user/client-interface';
import { Component, inject, Output } from '@angular/core';

import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatCardModule } from '@angular/material/card';
import { AdminService } from '../../interface/user/admin.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AccessInterface } from '../../interface/access/access-interface';


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

  constructor(private adminService: AdminService, private snackBar: MatSnackBar, private router: Router,){};

  private fb = inject(FormBuilder);
  clientForm = this.fb.group({
    name: ['', Validators.required],
    cpf: ['', Validators.required],
    email: ['', Validators.required],
    phone: ['', Validators.required],
    password: ['', Validators.required]
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
          this.snackBar.open('CPF já cadastrado', 'Fechar', {
            duration: 3000,
            verticalPosition: 'top',
            horizontalPosition: 'center'
          });
        } else {

          const access: AccessInterface = {
            email: client.email,
            password: client.password,
            role: 'client'
          };

          // Salva o cliente e o acesso
          this.adminService.saveClient(client).subscribe(
            () => {
              // Salva o acesso após o cliente ser salvo
              this.adminService.saveAccess(access).subscribe(
                () => {
                  this.snackBar.open('Usuário ' + client.name + ' cadastrado com sucesso', 'Fechar', {
                    duration: 3000,
                    verticalPosition: 'top',
                    horizontalPosition: 'center'
                  });
                  this.router.navigate(['/clientlist']);
                },
                (error) => {
                  console.error('Erro ao salvar acesso:', error);
                  this.snackBar.open('Erro ao salvar acesso', 'Fechar', {
                    duration: 3000,
                    verticalPosition: 'top',
                    horizontalPosition: 'center'
                  });
                }
              );
            },
            (error) => {
              console.error('Erro ao salvar cliente:', error);
              this.snackBar.open('Erro ao salvar cliente', 'Fechar', {
                duration: 3000,
                verticalPosition: 'top',
                horizontalPosition: 'center'
              });
            }
          );
        }
      },
      (error) => {
        if (error.status === 404) {
          this.adminService.saveClient(client).subscribe(
            () => {
              const access: AccessInterface = {
                email: client.email,
                password: client.password,
                role: 'client'
              };

              this.adminService.saveAccess(access).subscribe(
                () => {
                  this.snackBar.open('Usuário ' + client.name + ' cadastrado com sucesso', 'Fechar', {
                    duration: 3000,
                    verticalPosition: 'top',
                    horizontalPosition: 'center'
                  });
                  this.router.navigate(['/clientlist']);
                },
                (saveError) => {
                  console.error('Erro ao salvar acesso:', saveError);
                  this.snackBar.open('Erro ao salvar acesso', 'Fechar', {
                    duration: 3000,
                    verticalPosition: 'top',
                    horizontalPosition: 'center'
                  });
                }
              );
            },
            (saveError) => {
              console.error('Erro ao salvar cliente:', saveError);
              this.snackBar.open('Erro ao salvar cliente', 'Fechar', {
                duration: 3000,
                verticalPosition: 'top',
                horizontalPosition: 'center'
              });
            }
          );
        } else {
          console.error('Erro ao buscar cliente:', error);
          this.snackBar.open('Erro ao buscar cliente', 'Fechar', {
            duration: 3000,
            verticalPosition: 'top',
            horizontalPosition: 'center'
          });
        }
      }
    );
  }

}





