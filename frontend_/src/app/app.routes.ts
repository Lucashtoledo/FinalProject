import { Routes } from '@angular/router';
import { AdminComponent } from './components/admin/admin.component';
import { AddClientComponent } from './components/add-client/add-client.component';
import { AddFormComponent } from './components/add-form/add-form.component';
import { ListFormComponent } from './components/list-form/list-form.component';

export const routes: Routes = [
  {
    path: 'clientlist',
    component: AdminComponent
  },
  {
    path: 'add-client',
    component: AddClientComponent
  },
  {
    path: 'add-form',
    component: AddFormComponent
  },
  {
    path: 'list-form/:id',
    component: ListFormComponent
  }
];
