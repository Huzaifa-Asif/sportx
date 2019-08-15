import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardAdminComponent } from './dashboard-admin/dashboard-admin.component';
import { DashboardCashierComponent } from './dashboard-cashier/dashboard-cashier.component';
import { AdminGuard } from 'src/app/guards/admin/admin.guard';
import { CashierGuard } from 'src/app/guards/cashier/cashier.guard';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'admin',
        component: DashboardAdminComponent,
        canActivate: [AdminGuard]
      },
      {
        path: 'cashier',
        component: DashboardCashierComponent,
        canActivate: [CashierGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
