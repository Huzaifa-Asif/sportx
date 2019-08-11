import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CurrenciesListComponent } from './currencies-list/currencies-list.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'list',
        component: CurrenciesListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CurrenciesRoutingModule { }
