import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatTableModule, MatTable, MatTableDataSource } from '@angular/material/table';
import { MatPaginatorModule, MatPaginator } from '@angular/material/paginator';
import { MatSortModule, MatSort } from '@angular/material/sort';
import { AdminInterface } from '../../interface/user/admin-interface';
import { AdminService } from '../../interface/user/admin.service';
import { lastValueFrom } from 'rxjs';
import { MatButton } from '@angular/material/button';

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
  @ViewChild(MatTable) table!: MatTable<AdminInterface>;
  dataSource = new MatTableDataSource<AdminInterface>();

  constructor(private adminService: AdminService){};

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['id', 'name', 'email', 'phone', 'actions'];

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
    this.carregarDados();
  }

  async carregarDados(){
    const admins = await lastValueFrom(await this.adminService.getAll());
    this.dataSource = new MatTableDataSource<AdminInterface>(admins);
    this.table.dataSource = this.dataSource;
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  };

  async apagarDado(admin: AdminInterface){
    await lastValueFrom(this.adminService.delete(admin));
    console.log('Apagou o administrador', admin);
    await this.carregarDados();
  };

  async salvarDado(admin: AdminInterface){
    if (admin.id != null){
      await this.adminService.put(admin);
      console.log('Usuário ' + admin.name + ' atualizado')
    } else{
    await this.adminService.save(admin);
    console.log('Usuário ' + admin.name + ' criado')
    }
    await this.carregarDados();
  }

  editarDado(){};

}
