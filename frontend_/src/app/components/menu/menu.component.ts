import { Component } from '@angular/core';
import { MatListModule } from '@angular/material/list';

interface MenuItem{
  path: string;
  title: string;
}

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [MatListModule],
  template: `

        @for(menuItem of menuList; track menuItem){
          <a mat-list-item [href]="menuItem.path">{{menuItem.title}}</a>
        }
  `,
  styles: ``
})
export class MenuComponent {
  menuList: MenuItem[] = [
    {
      title:'Listar Clientes',
      path:'/clientlist'
    },
    {
      title: 'Adicionar Cliente',
      path: '/add-client'
    }
  ]
}
