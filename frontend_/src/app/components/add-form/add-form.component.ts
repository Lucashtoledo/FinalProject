import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { AdminService } from '../../interface/user/admin.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { ProcessRequestInterface } from '../../interface/process/processRequest-interface';

@Component({
  selector: 'app-add-form',
  templateUrl: './add-form.component.html',
  standalone: true,
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatCardModule,
    ReactiveFormsModule,
    MatFormFieldModule,
  ],
  styles: `
    .full-width {
      width: 100%;
    }

    .shipping-card {
      min-width: 120px;
      margin: 20px auto;
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
})
export class AddFormComponent {
  private fb = inject(FormBuilder);
  public clientId: string | null = null;

  clientForm = this.fb.group({
    processName: ['', Validators.required],
    numberProcess: ['', Validators.required],
    necessaryDoc: [''],
  });

  items: string[] = [];

  constructor(
    private adminService: AdminService,
    private route: ActivatedRoute,
    private router: Router // Injetar o Router
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.clientId = params.get('id');
    });
  }

  addItem() {
    const necessaryDoc = this.clientForm.value.necessaryDoc;
    if (necessaryDoc) {
      this.items.push(necessaryDoc);
      this.clientForm.patchValue({ necessaryDoc: '' });
    }
  }

  async onSubmit(): Promise<void> {
    if (this.clientForm.valid) {
      const formValues = this.clientForm.value;
      const clientId = Number(this.route.snapshot.paramMap.get('id'));

      const createProcessRequest: ProcessRequestInterface = {
        processName: formValues.processName || '',
        numberProcess: Number(formValues.numberProcess) || 0,
        formName: formValues.processName || '',
        necessaryDocs: this.items,
        clientId: clientId,
      };
      console.log(createProcessRequest);

      // Chama o serviço para criar o processo
      await this.adminService.saveProcess(createProcessRequest).toPromise();

      // Redireciona para a página ListFormComponent após o sucesso
      this.router.navigate(['/list-form', clientId]);

      alert('Processo criado com sucesso!');
    } else {
      alert('Por favor, preencha todos os campos obrigatórios.');
    }
  }
}
