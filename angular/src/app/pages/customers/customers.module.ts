import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DataTablesModule } from 'angular-datatables';

import { UsersRoutingModule } from './customers-routing.module';
import { UsersListComponent } from './customers-list/customers-list.component';
import { UsersAddComponent } from './customers-add/customers-add.component';
import { UsersEditComponent } from './customers-edit/customers-edit.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [UsersListComponent, UsersAddComponent, UsersEditComponent],
  imports: [
    DataTablesModule,
    CommonModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    UsersRoutingModule
  ],
  entryComponents: [UsersAddComponent, UsersEditComponent]
})
export class CustomersModule { }
