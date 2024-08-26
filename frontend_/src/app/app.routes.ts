import { Routes } from '@angular/router';
import { AdminComponent } from './components/admin/admin.component';
import { AddClientComponent } from './components/add-client/add-client.component';

import { ListFormComponent } from './components/list-form/list-form.component';
import { AddFormComponent } from './components/add-form/add-form.component';
import { ListDocComponent } from './components/list-doc/list-doc.component';

import { HomeComponent } from './home/home.component';




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
    path: 'add-form/:id',
    component: AddFormComponent
  },
  {
    path: 'list-form/:id',
    component: ListFormComponent
  },
  {
    path: 'ver-doc/:id',
    component: ListDocComponent
  },
  {
    path: 'admin-dashboard',
    component: HomeComponent
  },
];
