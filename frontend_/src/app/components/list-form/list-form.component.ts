import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTable, MatTableDataSource, MatTableModule } from '@angular/material/table';
import { AdminService } from '../../interface/user/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProcessInterface } from '../../interface/process/process-interface';
import { CommonModule } from '@angular/common';
import { lastValueFrom } from 'rxjs';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-list-form',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButton
  ],
  templateUrl: './list-form.component.html',
  styles: `   .full-width-table {
    width: 100%;
  }`
})
export class ListFormComponent implements AfterViewInit{
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<ProcessInterface>;
  dataSource = new MatTableDataSource<ProcessInterface>();
  clientId: string | null = null;

  constructor(private adminService: AdminService, private route: ActivatedRoute, private router: Router) {}

  displayedColumns = ['name', 'actions'];

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.clientId = params.get('id');
      this.carregarDados();
    });
  }

  ngAfterViewInit(): void {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
    this.carregarDados();
  }

  async carregarDados() {

        const process = await lastValueFrom(this.adminService.getProcessById(Number(this.clientId)));
        this.dataSource = new MatTableDataSource<ProcessInterface>(process);
        this.table.dataSource = this.dataSource;
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator; // Atualiza a dataSource com os dados recebidos

    }

  verDoc(processId: string) {
    this.router.navigate(['/ver-doc', processId])
  };
}
