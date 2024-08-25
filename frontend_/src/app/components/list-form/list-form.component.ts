import { Component, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTable, MatTableDataSource, MatTableModule } from '@angular/material/table';
import { AdminService } from '../../interface/user/admin.service';
import { ActivatedRoute } from '@angular/router';
import { ProcessInterface } from '../../interface/process/process-interface';
import { CommonModule } from '@angular/common';
import { lastValueFrom } from 'rxjs';

@Component({
  selector: 'app-list-form',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule
  ],
  templateUrl: './list-form.component.html',
  styles: ``
})
export class ListFormComponent {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<ProcessInterface>;
  dataSource = new MatTableDataSource<ProcessInterface>();
  clientId: string | null = null;

  constructor(private adminService: AdminService, private route: ActivatedRoute) {}

  displayedColumns = ['name', 'actions'];

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.clientId = params.get('id');
      console.log('Client ID:', this.clientId);  // Verifica se o ID está sendo obtido
      this.carregarDados();  // Carrega os dados logo após obter o ID
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  }

  async carregarDados() {
    if (this.clientId) {
      try {
        const process = await lastValueFrom(this.adminService.getProcessById(Number(this.clientId)));
        console.log('Dados recebidos:', process);  // Verifica os dados recebidos
        this.dataSource.data = process;  // Atualiza a dataSource com os dados recebidos
      } catch (error) {
        console.error('Erro ao carregar os dados:', error);
      }
    }
  }

  verDoc() {
    // Implementação do método verDoc
  }
}
