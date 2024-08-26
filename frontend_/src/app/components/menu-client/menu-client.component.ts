import { Component } from '@angular/core';
import { MatListModule } from '@angular/material/list';

interface MenuItem{
  path: string;
  title: string;
}

@Component({
  selector: 'app-menu-client',
  standalone: true,
  imports: [MatListModule],
  template: `

  @for(menuItem of menuList; track menuItem){
    <a mat-list-item [href]="menuItem.path">{{menuItem.title}}</a>
  }
`,

  styles: ``
})
export class MenuClientComponent {
  menuList: MenuItem[] = [
    {
      title:'Listar Processos',
      path:'/list-process-client'
    },
  ]
}
