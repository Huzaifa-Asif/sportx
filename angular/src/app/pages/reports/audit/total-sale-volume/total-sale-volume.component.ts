import { Component, OnInit } from '@angular/core';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { HelperService } from 'src/app/services/helper/helper.service';

@Component({
  selector: 'app-total-sale-volume',
  templateUrl: './total-sale-volume.component.html',
  styleUrls: ['./total-sale-volume.component.scss']
})
export class TotalSaleVolumeComponent implements OnInit {

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

      const dateToFormated = this.helper.getReportFormatedDate(this.dateTo);
      const dateFromFormated = this.helper.getReportFormatedDate(this.dateFrom);

      const data = {
        CoName: 'Islamabad Exchange',
        MonthName: 'June',
        FromDate: dateFromFormated,
        ToDate: dateToFormated,
        FileFormat: this.format
      };

      this._getReport(data);

    }
  }

  _getReport(data) {

    this.isRequested = false;

    this.api.getReport('TotalSaleVolume', data, 'TotalSaleVolume', this.format).then((response: any) => {

      this.isRequested = true;
      // console.log('Success', response);

    }, (error) => {

      this.isRequested = true;
      // console.log('Failure', error);

    });
  }

}
