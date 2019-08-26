import { Routes, RouterModule } from '@angular/router';

export const content: Routes = [
  {
    path: 'dashboard',
    loadChildren: './pages/dashboard/dashboard.module#DashboardModule'
  },
  {
    path: 'users',
    loadChildren: './pages/users/users.module#UsersModule'
  },
  {
    path: 'customers',
    loadChildren: './pages/customers/customers.module#CustomersModule'
  },
  {
    path: 'report',
    loadChildren: './pages/reports/reports.module#ReportsModule'
  },

];
