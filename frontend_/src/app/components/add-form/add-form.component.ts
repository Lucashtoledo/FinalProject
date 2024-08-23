import {ChangeDetectionStrategy, Component, inject} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { AdminService } from '../../interface/user/admin.service';
import { FormInterface } from '../../interface/form/form-interface';
import { CommonModule } from '@angular/common'; 

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
    ReactiveFormsModule
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
  constructor(private adminService: AdminService){};
  private fb = inject(FormBuilder);

  clientForm = this.fb.group({
    name: ['', Validators.required],
    necessaryDoc: [''],
  });

  items: string[] = [];

  addItem() {
    const necessaryDoc = this.clientForm.value.necessaryDoc;
    if (necessaryDoc) {
      this.items.push(necessaryDoc);
      this.clientForm.patchValue({ necessaryDoc: '' });
    }
  }

  async onSubmit() {
    const saveForm: FormInterface = {
      name: this.clientForm.value.name || '',
      necessaryDoc: this.items
    };

    try {
      await this.salvarDado(saveForm);
      alert('Formulário adicionado com sucesso!');
      this.clientForm.reset();
      this.items = [];
    } catch (error) {
      console.error('Erro ao salvar o formulário!', error);
    }
  }

  async salvarDado(form: FormInterface) {
    try {
      const response = await this.adminService.saveForm(form).toPromise();
      console.log('Formulário salvo com sucesso!', response);
    } catch (error) {
      console.error('Erro ao salvar o formulário!', error);
      throw error;
    }
  }
}
