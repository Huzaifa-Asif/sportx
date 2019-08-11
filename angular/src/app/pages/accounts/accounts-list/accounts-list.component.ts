import { Component, OnInit } from '@angular/core';
import { RestApiService } from 'src/app/services/api/rest-api.service';

@Component({
  selector: 'app-accounts-list',
  templateUrl: './accounts-list.component.html',
  styleUrls: ['./accounts-list.component.scss']
})
export class AccountsListComponent implements OnInit {

  accounts = [];

  tableOption: DataTables.Settings = {};

  constructor(private api: RestApiService) { }

  ngOnInit(): void {

    // Table option
    this.tableOption = {
      pagingType: 'full_numbers'
    };

    this.getAccounts();
  }

  getAccounts() {
    this.api.get('Accounts/All').then((data: any) => {
      // console.log('Data', data);
      this.accounts = data;
    }).catch(err => console.log('Error', err));
  }


}
