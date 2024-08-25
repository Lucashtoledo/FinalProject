  /*
  import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
  import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
  import { MatButtonModule } from '@angular/material/button';
  import { MatCardModule } from '@angular/material/card';
  import { MatInputModule } from '@angular/material/input';
  import { MatSelectModule } from '@angular/material/select';
  import { MatFormFieldModule } from '@angular/material/form-field';
  import { AdminService } from '../../interface/user/admin.service';
  import { FormInterface } from '../../interface/form/form-interface';
  import { CommonModule } from '@angular/common';
  import { ActivatedRoute } from '@angular/router';
  import { ProcessInterface } from '../../interface/process/process-interface';

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
      name: ['', Validators.required],
      necessaryDoc: [''],
      processName: ['', Validators.required],
      numberProcess: [''],
    });

    items: string[] = [];

    constructor(private adminService: AdminService, private route: ActivatedRoute) {}

    ngOnInit() {
      this.route.paramMap.subscribe(params => {
        this.clientId = params.get('id');
      });
    }

    // Método para adicionar os itens no array "items"
    addItem() {
      const necessaryDoc = this.clientForm.value.necessaryDoc;
      if (necessaryDoc) {
        this.items.push(necessaryDoc);
        this.clientForm.patchValue({ necessaryDoc: '' });
      }
    }

    async onSubmit(): Promise<void> {
      if (this.clientForm.valid) {
        try {
          const formValues = this.clientForm.value;

          const clientId = Number(this.route.snapshot.paramMap.get('id'));
          // Teste id do cliente
          console.log(clientId);

          // Cria o objeto do formulário
          const newForm: FormInterface = {
            name: formValues.name || '',
            necessaryDoc: this.items
          };

          // Salva o formulário e obtém a resposta com o ID gerado
          const formResponse = await this.adminService.saveForm(newForm).toPromise();
          // Teste id do formulario
          console.log(formResponse?.id);

          if (!formResponse || formResponse.id === undefined) {
            throw new Error('Erro ao salvar o formulário: ID do formulário é indefinido.');
          }

          // cria o processo usando o ID do formulário gerado
          const newProcess: Omit<ProcessInterface, 'id'> = {
            processName: formValues.processName || '',
            numberProcess: Number(formValues.numberProcess) || 0,
            clientId: Number(clientId),
            formId: Number(formResponse.id),
          };
          // teste valores enviados
          console.log(newProcess)

          // Salva o processo
          await this.saveProcess(newProcess);

          alert('Processo criado com sucesso!');
        } catch (error) {
          console.error('Erro ao salvar o formulário ou processo:', error);
          alert('Ocorreu um erro ao criar o processo. Por favor, tente novamente.');
        }
      } else {
        alert('Por favor, preencha todos os campos obrigatórios.');
      }
    }


    private async saveProcess(newProcess: ProcessInterface): Promise<void> {
      try {
        // Salva o processo com todos os dados completos
        await this.adminService.saveProcess(newProcess).toPromise();
        console.log('Processo criado com sucesso!');
      } catch (error) {
        console.error('Erro ao salvar o processo:', error);
        throw error; // Repassa o erro para ser tratado no onSubmit
      }
    }
  }
    */
  import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
  import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
  import { MatButtonModule } from '@angular/material/button';
  import { MatCardModule } from '@angular/material/card';
  import { MatInputModule } from '@angular/material/input';
  import { MatSelectModule } from '@angular/material/select';
  import { MatFormFieldModule } from '@angular/material/form-field';
  import { AdminService } from '../../interface/user/admin.service';
  import { CommonModule } from '@angular/common';
  import { ActivatedRoute } from '@angular/router';
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
      formName: ['', Validators.required],
      necessaryDoc: [''],
    });

    items: string[] = [];

    constructor(private adminService: AdminService, private route: ActivatedRoute) {}

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
        try {
          const formValues = this.clientForm.value;

          const clientId = Number(this.route.snapshot.paramMap.get('id'));

          // Cria o objeto de request para criar um processo legal
          const createProcessRequest: ProcessRequestInterface = {
            processName: formValues.processName || '',
            numberProcess: Number(formValues.numberProcess) || 0,
            formName: formValues.formName || '',
            necessaryDocs: this.items,
            clientId: clientId,
          };
          console.log(createProcessRequest);

          // Chama o serviço para criar o processo
          await this.adminService.saveProcess(createProcessRequest).toPromise();

          alert('Processo criado com sucesso!');
        } catch (error) {
          console.error('Erro ao criar o processo:', error);
          alert('Ocorreu um erro ao criar o processo. Por favor, tente novamente.');
        }
      } else {
        alert('Por favor, preencha todos os campos obrigatórios.');
      }
    }
  }


