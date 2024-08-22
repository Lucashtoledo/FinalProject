import { Routes } from '@angular/router';
import { AdminComponent } from './components/admin/admin.component';
import { AddClientComponent } from './components/add-client/add-client.component';

export const routes: Routes = [
  {
    path: 'adminlist',
    component: AdminComponent
  },
  {
    path: 'add-client',
    component: AddClientComponent
  }
];
