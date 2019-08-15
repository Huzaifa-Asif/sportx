import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TransactionsRoutingModule } from './transactions-routing.module';
import { BillTransactionComponent } from './bill-transaction/bill-transaction.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SuspiciousTransactionComponent } from './suspicious-transaction/suspicious-transaction.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SectionComponent } from './components/section/section.component';

@NgModule({
  declarations: [BillTransactionComponent, SuspiciousTransactionComponent, SectionComponent],
  imports: [
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    TransactionsRoutingModule
  ],
  entryComponents: [SuspiciousTransactionComponent]
})
export class TransactionsModule { }
