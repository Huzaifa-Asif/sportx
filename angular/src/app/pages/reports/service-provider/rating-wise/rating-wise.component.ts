import { Component, OnInit } from '@angular/core';
import { RestApiService } from '../../../../services/api/rest-api.service';
import { HelperService } from '../../../../services/helper/helper.service';

@Component({
  selector: 'app-rating-wise',
  templateUrl: './rating-wise.component.html',
  styleUrls: ['./rating-wise.component.scss']
})
export class RatingWise implements OnInit {

 
  constructor(private api: RestApiService, private helper: HelperService) { }

  ngOnInit() {
  }

  

}
