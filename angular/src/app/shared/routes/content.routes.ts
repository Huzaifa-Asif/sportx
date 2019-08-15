import { Routes, RouterModule } from '@angular/router';

export const content: Routes = [
  {
    path: 'dashboard',
    loadChildren: './pages/dashboard/dashboard.module#DashboardModule'
  },
  {
    path: 'currencies',
    loadChildren: './pages/currencies/currencies.module#CurrenciesModule'
  },
  {
    path: 'accounts',
    loadChildren: './pages/accounts/accounts.module#AccountsModule'
  },
  {
    path: 'users',
    loadChildren: './pages/users/users.module#UsersModule'
  },
  {
    path: 'voucher',
    loadChildren: './pages/vouchers/vouchers.module#VouchersModule'
  },
  {
    path: 'report',
    loadChildren: './pages/reports/reports.module#ReportsModule'
  },
  {
    path: 'transactions',
    loadChildren: './pages/transactions/transactions.module#TransactionsModule'
  },
];
