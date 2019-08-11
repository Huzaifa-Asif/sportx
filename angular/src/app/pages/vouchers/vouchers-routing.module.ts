import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VouchersGenerateComponent } from './vouchers-generate/vouchers-generate.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'generate',
        component: VouchersGenerateComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VouchersRoutingModule { }
