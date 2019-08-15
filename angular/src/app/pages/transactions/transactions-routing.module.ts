import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BillTransactionComponent } from './bill-transaction/bill-transaction.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'bill',
        component: BillTransactionComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TransactionsRoutingModule { }
