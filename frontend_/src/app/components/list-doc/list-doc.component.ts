import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTable, MatTableDataSource, MatTableModule } from '@angular/material/table';
import { AdminService } from '../../interface/user/admin.service';
import { ActivatedRoute, Router } from '@angular/router';
import { DocumentInterface } from '../../interface/document/document-interface';
import { CommonModule } from '@angular/common';
import { lastValueFrom } from 'rxjs';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-list-doc',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatButton
  ],
  templateUrl: './list-doc.component.html',
  styles: ``
})
export class ListDocComponent implements AfterViewInit {
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatTable) table!: MatTable<DocumentInterface>;
  dataSource = new MatTableDataSource<DocumentInterface>();
  processId: string | null = null;

  displayedColumns = ['name', 'actions'];

  constructor(private adminService: AdminService, private route: ActivatedRoute, private router: Router) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.processId = params.get('id');
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
    const doc = await lastValueFrom(this.adminService.getDocByProcessId(Number(this.processId)));
    this.dataSource = new MatTableDataSource<DocumentInterface>(doc);
    this.table.dataSource = this.dataSource;
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  downloadDocument(docId: number): void {
    this.adminService.downloadFile(docId).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `document_${docId}.pdf`;
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
    }, error => {
      console.error('Erro ao fazer download do arquivo', error);
    });
  }

  onFileSelected(event: Event, document: DocumentInterface): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];

      if (file.type === 'application/pdf') {
        this.uploadFile(file, document);
      } else {
        alert('Please upload a PDF file.');
      }
    }
  }

  async uploadFile(file: File, document: DocumentInterface): Promise<void> {
    const formData = new FormData();

    formData.append('file', file);
    formData.append('docId', document.id.toString());  // ID do documento

    try {
      await lastValueFrom(this.adminService.uploadDocument(formData));
      // Atualize a lista de documentos ou mostre uma mensagem de sucesso
    } catch (error) {
      console.error('Erro ao fazer upload do arquivo:', error);
    }
  }

  async downloadFilePart(docId: number): Promise<void> {
    try {
      const totalParts = 5; // Supondo que sabemos que há 5 partes (20 MB no total)
      const chunks: Blob[] = [];

      for (let i = 0; i < totalParts; i++) {
        const blobPart = await lastValueFrom(this.adminService.downloadFilePart(docId, i));
        chunks.push(blobPart);
      }

      const finalBlob = new Blob(chunks, { type: 'application/pdf' });
      this.saveFile(finalBlob, `document_${docId}.pdf`);
    } catch (error) {
      console.error('Erro ao fazer download do arquivo', error);
    }
  }
  private saveFile(blob: Blob, fileName: string) {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = fileName;
    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
  }

}
