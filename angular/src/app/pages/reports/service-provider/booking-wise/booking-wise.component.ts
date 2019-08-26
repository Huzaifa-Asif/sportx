import { Component, OnInit } from '@angular/core';
import { RestApiService } from '../../../../services/api/rest-api.service';
import { HelperService } from '../../../../services/helper/helper.service';

@Component({
  selector: 'app-booking-wise',
  templateUrl: './booking-wise.component.html',
  styleUrls: ['./booking-wise.component.scss']
})
export class BookingWise implements OnInit {



  constructor(private api: RestApiService, private helper: HelperService) { }

  ngOnInit() {
  }

  

}
