import { Component, OnInit } from '@angular/core';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { HelperService } from 'src/app/services/helper/helper.service';

@Component({
  selector: 'app-weekly-d',
  templateUrl: './weekly-d.component.html',
  styleUrls: ['./weekly-d.component.scss']
})
export class WeeklyDComponent implements OnInit {

  isRequested = true;
  submitted = false;

  format: any = 'pdf';
  dateTo: any = '';
  dateFrom: any = '';

  constructor(private api: RestApiService, private helper: HelperService) { }

  ngOnInit() {
  }

  getReport() {

    this.submitted = true;

    if (this.format !== '' && this.dateTo !== '' && this.dateFrom !== '') {

      const tempDateTo = new Date(this.dateTo);
      const dateToFormated = tempDateTo.toJSON();

      const tempDateFrom = new Date(this.dateFrom);
      const dateFromFormated = tempDateFrom.toJSON();

      const data = {
        CoName: 'Islamabad Exchange',
        BranchName: 'Head Office',
        FromDate: dateFromFormated,
        ToDate: dateToFormated,
        FileFormat: this.format,
        BranchId: 1,
      };

      this._getReport(data);

    }
  }

  _getReport(data) {

    this.isRequested = false;

    this.api.getReport('WeeklyD', data, 'WeeklyD', this.format).then((response: any) => {

      this.isRequested = true;
      // console.log('Success', response);

    }, (error) => {

      this.isRequested = true;
      // console.log('Failure', error);

    });
  }


}
