import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CurrenciesRoutingModule } from './currencies-routing.module';
import { CurrenciesListComponent } from './currencies-list/currencies-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DataTablesModule } from 'angular-datatables';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { CurrenciesAddComponent } from './currencies-add/currencies-add.component';
import { CurrenciesEditComponent } from './currencies-edit/currencies-edit.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [CurrenciesListComponent, CurrenciesAddComponent, CurrenciesEditComponent],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    DataTablesModule,
    Ng2SmartTableModule,
    NgbModule,
    CommonModule,
    CurrenciesRoutingModule
  ],
  entryComponents: [CurrenciesEditComponent, CurrenciesAddComponent]
})
export class CurrenciesModule { }
