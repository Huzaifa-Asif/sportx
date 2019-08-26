import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportsRoutingModule } from './reports-routing.module';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';


import { BookingWise } from './service-provider/booking-wise/booking-wise.component';
import { RatingWise } from './service-provider/rating-wise/rating-wise.component';

@NgModule({
  // tslint:disable-next-line: max-line-length
  declarations: [BookingWise, RatingWise],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    ReportsRoutingModule
  ]
})
export class ReportsModule { }
