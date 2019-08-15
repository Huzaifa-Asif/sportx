import { Component, OnInit } from '@angular/core';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { HelperService } from 'src/app/services/helper/helper.service';
import { CurrenciesEditComponent } from '../currencies-edit/currencies-edit.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CurrenciesAddComponent } from '../currencies-add/currencies-add.component';

@Component({
  selector: 'app-currencies-list',
  templateUrl: './currencies-list.component.html',
  styleUrls: ['./currencies-list.component.scss']
})
export class CurrenciesListComponent implements OnInit {

  currencies = [];

  settings = {
    columns: {
      CurrencyCode: {
        title: 'Code',
        editable: false
      },
      ShortName: {
        title: 'Short Name',
        editable: false
      },
      FullName: {
        title: 'Full Name',
        editable: false
      },
      BuyHighRate: {
        title: 'Buy High Rate'
      },
      BuyLowRate: {
        title: 'Buy Low Rate'
      },
      SellHighRate: {
        title: 'Sell High Rate'
      },
      SellLowRate: {
        title: 'Sell Low Rate'
      }
    },
    actions: {
      add: false,
      edit: true,
      delete: false
    },
    edit: {
      confirmSave: true,
      editButtonContent: 'Update',
      saveButtonContent: 'Save',
      cancelButtonContent: 'Cancel'
    }
  };

  tableOption: DataTables.Settings = {};

  isDataLoaded = false;

  userId;

  selectedCurrencyId;

  constructor(private api: RestApiService, public auth: AuthService, private router: Router, private helper: HelperService,
    private modalService: NgbModal) {
    if (this.auth.user.UserID) {
      this.userId = this.auth.user.UserID;
    } else {
      this.router.navigateByUrl('auth/login');
    }

    // Table option
    this.tableOption = {
      pagingType: 'full_numbers'
    };
  }

  ngOnInit(): void {
    this.getCurrencies();
  }

  getCurrencies() {
    this.api.get('Currencies/List').then((data: any) => {
      // console.log('Data', data);
      this.currencies = data;
      this.isDataLoaded = true;
    }).catch(err => console.log('Error', err));
  }

  onSaveConfirm(event) {

    const data = {
      userId: this.userId,
      currencyId: event.newData.CurrencyId,
      buyHighRate: event.newData.BuyHighRate,
      buyLowRate: event.newData.BuyLowRate,
      sellHighRate: event.newData.SellHighRate,
      sellLowRate: event.newData.SellLowRate
    };

    // console.log('Data', data);

    this._sendQuickUpdateRequest(data, event.newData.FullName);

  }

  _sendQuickUpdateRequest(data, currencyName) {
    this.api.post('Currencies/QuickUpdate', data).then((response: any) => {

      this.helper.successBigToast('Success', 'Successfully updated: ' + currencyName);
      this.ngOnInit();

    }, () => {
      this.helper.failureBigToast('Failed!', 'Invalid data, kindly check all new rates.');
    });
  }

  selectedCurrency(event) {
    const currency = event.data;
    this._openEditModal(currency);
  }

  _openEditModal(currency) {
    const modalRef = this.modalService.open(CurrenciesEditComponent);
    modalRef.componentInstance.currency = currency;

    modalRef.result.then(() => { this.ngOnInit(); }, () => { this.ngOnInit(); });
  }

  openAddModal() {
    const modalRef = this.modalService.open(CurrenciesAddComponent);

    modalRef.result.then(() => { this.ngOnInit(); }, () => { this.ngOnInit(); });
  }

}
