import { Component, OnInit } from '@angular/core';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { HelperService } from 'src/app/services/helper/helper.service';

@Component({
  selector: 'app-statement-f',
  templateUrl: './statement-f.component.html',
  styleUrls: ['./statement-f.component.scss']
})
export class StatementFComponent implements OnInit {

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

      const data = {
        CoName: 'Islamabad Exchange',
        MonthName: 'June',
        FromDate: this.helper.getReportFormatedDateYMD(this.dateFrom),
        ToDate: this.helper.getReportFormatedDateYMD(this.dateTo),
        FileFormat: this.format,
      };

      this._getReport(data);

    }
  }

  _getReport(data) {

    this.isRequested = false;

    this.api.getReport('StatementF', data, 'StatementF', this.format).then((response: any) => {

      this.isRequested = true;
      // console.log('Success', response);

    }, (error) => {

      this.isRequested = true;
      // console.log('Failure', error);

    });
  }


}
