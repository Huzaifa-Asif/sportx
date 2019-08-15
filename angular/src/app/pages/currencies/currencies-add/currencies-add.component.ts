import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { HelperService } from 'src/app/services/helper/helper.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-currencies-add',
  templateUrl: './currencies-add.component.html',
  styleUrls: ['./currencies-add.component.scss']
})
export class CurrenciesAddComponent implements OnInit {

  currencyForm: FormGroup;

  submitted = false;
  isDataLoaded = false;
  isRequested = true;

  regions = [];
  types = [];

  userId;

  constructor(private fb: FormBuilder, private api: RestApiService, private helper: HelperService,
    private auth: AuthService, private router: Router, private activeModal: NgbActiveModal) {
    if (this.auth.user.UserID) {
      this.userId = this.auth.user.UserID;
    } else {
      this.router.navigateByUrl('auth/login');
    }
  }

  ngOnInit() {

    this.submitted = false;
    this.isDataLoaded = false;

    this.getCurrenciesData();

    this.currencyForm = this.fb.group({
      shortName: ['', Validators.required],
      fullName: ['', Validators.required],
      printName: ['', Validators.required],
      buyHighRate: ['', Validators.required],
      buyLowRate: ['', Validators.required],
      sellHighRate: ['', Validators.required],
      sellLowRate: ['', Validators.required],
      regionId: ['', Validators.required],
      currencyTypeId: ['', Validators.required],
      currencyFormula: ['*', Validators.required],
      userId: [this.userId, Validators.required]
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

  get f() { return this.currencyForm.controls; }

  onSubmit() {
    this.submitted = true;
    // console.log('Form Values', this.currencyForm.value);

    if (this.currencyForm.valid) {
      this.isRequested = false;
      this._sendSaveRequest(this.currencyForm.value);
    }
  }

  _sendSaveRequest(data) {
    this.api.post('Currencies/Add', data).then((response: any) => {

      this.helper.successBigToast('Success', 'Successfully added!');
      this.ngOnInit();

      this.isRequested = true;

    }, () => {
      this.helper.failureBigToast('Failed!', 'Invalid data, kindly check all fields.');
    });
  }

  closeMe() {
    this.activeModal.close();
  }
}
