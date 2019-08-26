import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UsersListComponent } from './users-list/users-list.component';
import { AdminGuard } from '../../guards/admin/admin.guard';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'list',
        component: UsersListComponent,
        canActivate: [AdminGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }
