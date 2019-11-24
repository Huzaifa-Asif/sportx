import { Component, OnInit } from '@angular/core';
import { HelperService } from '../../../services/helper/helper.service';
import * as Chartist from 'chartist';
import { ChartEvent, ChartType } from 'ng-chartist';
import { RestApiService } from '../../../services/api/rest-api.service';

export interface Chart {
  type: ChartType;
  data: Chartist.IChartistData;
  options?: any;
  responsiveOptions?: any;
  events?: ChartEvent;
}

@Component({
  selector: 'app-dashboard-admin',
  templateUrl: './dashboard-admin.component.html',
  styleUrls: ['./dashboard-admin.component.scss']
})
export class DashboardAdminComponent implements OnInit {

  lastRefreshed;


  total_tournaments;
  total_bookings;
  total_serviceProviders;
  total_customers;

  constructor(private helper: HelperService,  private api: RestApiService) { }

  ngOnInit() {
    this.lastRefreshed = this.helper.getCurrentTime();
    
    this.getServiceProviderData();
    this.getCustomerData();
    this.getBookingData();
    this.getTournamentData();

    
  }


  getServiceProviderData() {

    this.api.get('serviceProvider/get_serviceProvider').then((data: any) => {
      // console.log('Data', data);
      this.total_serviceProviders=data.length;
      console.log("service Provider"+this.total_serviceProviders);
    }).catch(err => console.log('Error', err));
  }

  getCustomerData() {

    this.api.get('customer/get_customer').then((data: any) => {
      // console.log('Data', data);
      this.total_customers=data.length;
      console.log("customer"+this.total_serviceProviders);
    }).catch(err => console.log('Error', err));
  }

  getBookingData() {

    this.api.get('bookingDetails/get_booking').then((data: any) => {
      // console.log('Data', data);
      this.total_bookings=data.length;
      console.log("booking"+this.total_serviceProviders);
    }).catch(err => console.log('Error', err));
  }

  getTournamentData() {

    this.api.get('tournament/get_tournament').then((data: any) => {
      // console.log('Data', data);
      this.total_tournaments=data.length;
      console.log("tournament"+this.total_serviceProviders);
    }).catch(err => console.log('Error', err));
  }


}
