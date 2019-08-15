import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { HelperService } from 'src/app/services/helper/helper.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-currencies-edit',
  templateUrl: './currencies-edit.component.html',
  styleUrls: ['./currencies-edit.component.scss']
})
export class CurrenciesEditComponent implements OnInit {

  submitted = false;
  isDataLoaded = false;
  isRequested = true;

  userId;
  currencyForm: FormGroup;

  regions = [];
  types = [];

  @Input() currency;

  constructor(private fb: FormBuilder, private api: RestApiService, private helper: HelperService,
    private auth: AuthService, private router: Router, private activeModal: NgbActiveModal) {
    if (this.auth.user.UserID) {
      this.userId = this.auth.user.UserID;
    } else {
      this.router.navigateByUrl('auth/login');
    }
  }

  get f() { return this.currencyForm.controls; }

  ngOnInit() {

    // console.log('currency', this.currency);

    this.submitted = false;
    this.isDataLoaded = false;

    this.getCurrenciesData();

    this.currencyForm = this.fb.group({
      shortName: [this.currency.ShortName, Validators.required],
      fullName: [this.currency.FullName, Validators.required],
      printName: [this.currency.PrintCode, Validators.required],
      buyHighRate: [this.currency.BuyHighRate, Validators.required],
      buyLowRate: [this.currency.BuyLowRate, Validators.required],
      sellHighRate: [this.currency.SellHighRate, Validators.required],
      sellLowRate: [this.currency.SellLowRate, Validators.required],
      regionId: [this.currency.RegionId, Validators.required],
      currencyTypeId: [this.currency.CurrencyTypeId, Validators.required],
      currencyFormula: [this.currency.Formula, Validators.required],
      userId: [this.userId, Validators.required],
      currencyId: [this.currency.CurrencyId, Validators.required]
    });

  }

  getCurrenciesData() {
    this.api.get('Currencies/GetData').then((data: any) => {
      // console.log('Data', data);
      this.regions = data.regions;
      this.types = data.types;
      this.isDataLoaded = true;
    }).catch(err => console.log('Error', err));
  }

  closeMe() {
    this.activeModal.close();
  }

  onSubmit() {
    this.submitted = true;
    // console.log('Form Values', this.currencyForm.value);

    if (this.currencyForm.valid) {
      this.isRequested = false;

      const currencyName = this.currency.FullName;

      this._sendUpdateRequest(this.currencyForm.value, currencyName);
    }
  }

  _sendUpdateRequest(data, currencyName) {
    this.api.post('Currencies/Update', data).then((response: any) => {

      this.isRequested = true;
      this.helper.successBigToast('Success', 'Successfully updated: ' + currencyName);

    }, () => {
      this.isRequested = true;
      this.helper.failureBigToast('Failed!', 'Invalid data, kindly check updated data.');
    });
  }

}
