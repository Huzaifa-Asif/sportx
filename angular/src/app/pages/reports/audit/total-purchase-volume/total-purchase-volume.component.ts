import { Component, OnInit } from '@angular/core';
import { RestApiService } from '../../../../services/api/rest-api.service';
import { HelperService } from '../../../../services/helper/helper.service';

@Component({
  selector: 'app-total-purchase-volume',
  templateUrl: './total-purchase-volume.component.html',
  styleUrls: ['./total-purchase-volume.component.scss']
})
export class TotalPurchaseVolumeComponent implements OnInit {

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

    this.api.getReport('TotalPurchaseVolume', data, 'TotalPurchaseVolume', this.format).then((response: any) => {

      this.isRequested = true;
      // console.log('Success', response);

    }, (error) => {

      this.isRequested = true;
      // console.log('Failure', error);

    });
  }


}
