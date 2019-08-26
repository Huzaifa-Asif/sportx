import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BookingWise } from './service-provider/booking-wise/booking-wise.component';
import { RatingWise } from './service-provider/rating-wise/rating-wise.component';


const routes: Routes = [
  {
    path: '',
    children: 
    [
      {
        path: 'booking_wise',
        component: BookingWise
      },
      {
        path: 'rating_wise',
        component: RatingWise
      },
     
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReportsRoutingModule { }
