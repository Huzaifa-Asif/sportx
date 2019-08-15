import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { VouchersRoutingModule } from './vouchers-routing.module';
import { VouchersGenerateComponent } from './vouchers-generate/vouchers-generate.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [VouchersGenerateComponent],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    VouchersRoutingModule
  ]
})
export class VouchersModule { }
