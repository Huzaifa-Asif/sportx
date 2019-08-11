import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountsRoutingModule } from './accounts-routing.module';
import { AccountsListComponent } from './accounts-list/accounts-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';

@NgModule({
  declarations: [AccountsListComponent],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    DataTablesModule,
    CommonModule,
    AccountsRoutingModule
  ]
})
export class AccountsModule { }
