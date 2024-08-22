import { DataSource } from '@angular/cdk/collections';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { map } from 'rxjs/operators';
import { Observable, of as observableOf, merge } from 'rxjs';
import { AdminInterface } from '../../interface/user/admin-interface';

// Inicializa um array de exemplo vazio de AdminInterface
const EXAMPLE_DATA: AdminInterface[] = [];

/**
 * Classe `AdminDataSource` que estende a classe `DataSource` do Angular CDK.
 * Essa classe gerencia os dados que são exibidos em uma tabela, com suporte a paginação e ordenação.
 */
export class AdminDataSource extends DataSource<AdminInterface> {
  data: AdminInterface[] = EXAMPLE_DATA;  // Dados que serão usados na tabela
  paginator: MatPaginator | undefined;    // Paginator para gerenciar a paginação
  sort: MatSort | undefined;              // Sort para gerenciar a ordenação

  constructor() {
    super(); // Chama o construtor da classe base `DataSource`
  }

  /**
   * Método `connect` que é chamado pelo Angular Material para se conectar à fonte de dados.
   * Ele retorna um Observable que emite os dados da tabela quando há mudanças na paginação ou ordenação.
   */
  connect(): Observable<AdminInterface[]> {
    if (this.paginator && this.sort) {
      // Combina os observables de dados, paginação e ordenação
      return merge(observableOf(this.data), this.paginator.page, this.sort.sortChange)
        .pipe(map(() => {
          // Retorna os dados paginados e ordenados
          return this.getPagedData(this.getSortedData([...this.data ]));
        }));
    } else {
      throw Error('Please set the paginator and sort on the data source before connecting.');
      // Lança um erro se `paginator` e `sort` não estiverem definidos
    }
  }

  /**
   * Método `disconnect` que é chamado pelo Angular Material quando a tabela é destruída.
   * Pode ser usado para limpar recursos, embora aqui esteja vazio.
   */
  disconnect(): void {}

  /**
   * Método privado `getPagedData` que retorna os dados da página atual.
   * Ele usa o `paginator` para calcular o índice de início e o número de itens por página.
   */
  private getPagedData(data: AdminInterface[]): AdminInterface[] {
    if (this.paginator) {
      const startIndex = this.paginator.pageIndex * this.paginator.pageSize;
      // Retorna os dados da página atual usando `splice` para cortar o array
      return data.splice(startIndex, this.paginator.pageSize);
    } else {
      return data; // Retorna todos os dados se `paginator` não estiver definido
    }
  }

  /**
   * Método privado `getSortedData` que retorna os dados ordenados com base no estado atual do `sort`.
   * Ele usa a função `compare` para comparar valores e determinar a ordem.
   */
  private getSortedData(data: AdminInterface[]): AdminInterface[] {
    if (!this.sort || !this.sort.active || this.sort.direction === '') {
      return data; // Retorna os dados sem ordenar se `sort` não estiver definido ou inativo
    }

    return data.sort((a, b) => {
      const isAsc = this.sort?.direction === 'asc';
      // Verifica o campo ativo e ordena os dados com base nele
      switch (this.sort?.active) {
        case 'name': return compare(a.name, b.name, isAsc);
        case 'email': return compare(a.email, b.email, isAsc);
        case 'phone': return compare(a.phone, b.phone, isAsc);
        case 'id': return compare(+a.id, +b.id, isAsc);
        default: return 0; // Retorna 0 para não alterar a ordem
      }
    });
  }
}

/**
 * Função `compare` que compara dois valores (strings ou números) e retorna:
 * -1 se `a` for menor que `b`,
 * 1 se `a` for maior que `b`,
 * Multiplicado por 1 ou -1 dependendo se a ordenação é ascendente ou descendente.
 */
function compare(a: string | number, b: string | number, isAsc: boolean): number {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
