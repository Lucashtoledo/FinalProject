import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatTableModule, MatTable, MatTableDataSource } from '@angular/material/table';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';
import { MatSortModule, MatSort } from '@angular/material/sort';
import { AdminService } from '../../interface/user/admin.service';
import { lastValueFrom } from 'rxjs';
import { MatButton } from '@angular/material/button';
import { ClientInterface } from '../../interface/user/client-interface';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styles: `
    .full-width-table {
      width: 100%;
    }
  `,
  standalone: true,
  imports: [MatTableModule, MatPaginatorModule, MatSortModule, MatButton]
})
export class AdminComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<ClientInterface>;
  dataSource = new MatTableDataSource<ClientInterface>();

  constructor(private adminService: AdminService, private router: Router) {};

  displayedColumns = ['id', 'name', 'email', 'phone', 'actions'];

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
    this.carregarDados();
  }

  async carregarDados(){
    const clients = await lastValueFrom(await this.adminService.getAllClient());
    this.dataSource = new MatTableDataSource<ClientInterface>(clients);
    this.table.dataSource = this.dataSource;
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  };

 // async salvarDado(client: ClientInterface){
  //  if (client.id != null){
    //  await this.adminService.put(client);
    //  console.log('Usuário ' + client.name + ' atualizado')
    //} else{
    //await this.adminService.save(client);
    //console.log('Usuário ' + client.name + ' criado')
    //}
    //await this.carregarDados();
 // }

  verFormularios(clientId: number) {
    this.router.navigate(['/list-form', clientId]);
  }

  addProcess(clientId: string){
    this.router.navigate(['/add-form', clientId]);
  }
}
