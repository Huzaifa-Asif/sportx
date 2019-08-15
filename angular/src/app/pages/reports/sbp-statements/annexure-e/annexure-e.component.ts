import { Component, OnInit } from '@angular/core';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { HelperService } from 'src/app/services/helper/helper.service';

@Component({
  selector: 'app-annexure-e',
  templateUrl: './annexure-e.component.html',
  styleUrls: ['./annexure-e.component.scss']
})
export class AnnexureEComponent implements OnInit {
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
        FromDate: this.helper.getReportFormatedDateYMD(this.dateFrom),
        ToDate: this.helper.getReportFormatedDateYMD(this.dateTo),
        FileFormat: this.format,
      };

      this._getReport(data);

    }
  }

  _getReport(data) {

    this.isRequested = false;

    this.api.getReport('AnnexureEReport', data, 'AnnexureEReport', this.format).then((response: any) => {

      this.isRequested = true;
      // console.log('Success', response);

    }, (error) => {

      this.isRequested = true;
      // console.log('Failure', error);

    });
  }


}
