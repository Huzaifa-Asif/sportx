import { Routes, RouterModule } from '@angular/router';

export const full: Routes = [
  {
    path: 'auth',
    loadChildren: './pages/auth/auth.module#AuthModule'
  },
  // {
  //   path: 'error',
  //   loadChildren: './pages/error-pages/error-pages.module#ErrorPagesModule'
  // },
  // {
  //   path: 'comingsoon',
  //   loadChildren: './pages/coming-soon/coming-soon.module#ComingSoonModule'
  // },
  // {
  //   path: 'maintenance',
  //   loadChildren: './pages/maintenance/maintenance.module#MaintenanceModule'
  // }
];
