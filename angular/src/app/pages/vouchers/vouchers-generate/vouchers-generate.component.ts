import { Component, OnInit } from '@angular/core';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { HelperService } from 'src/app/services/helper/helper.service';

@Component({
  selector: 'app-vouchers-generate',
  templateUrl: './vouchers-generate.component.html',
  styleUrls: ['./vouchers-generate.component.scss']
})
export class VouchersGenerateComponent implements OnInit {

  transactionType;
  customerType;
  oldCustomer;
  purpose;
  country;
  documentType;
  documentNumber;
  suspiciousTransaction;
  fcAmount;
  exRate;
  total;
  lcCurrency;
  fcCurrency;
  minRate;
  maxRate;

  isSubmitted = false;

  customer = {
    remitterId: '',
    name: '',
    contactNo: '',
    address: '',
  };

  currency = {
    currencyId: '',
    currencyCode: '',
    currencyName: '',
    BuyHighRate: 0,
    BuyLowRate: 0,
    SellHighRate: 0,
    SellLowRate: 0
  };

  validation = {
    minMax: false,
    stock: false,
    minMaxError: '',
    stockError: ''
  };

  customerTypesArray = [];
  customersArray = [];
  purposesArray = [];
  purposesAllArray = [];
  countriesArray = [];
  documentTypesArray = [];
  currenciesArray = [];
  stockArray = [];

  UserID;
  BranchID;

  isDataLoaded = false;

  constructor(private api: RestApiService, private auth: AuthService,
    private router: Router, private helper: HelperService) {
    if (this.auth.user.UserID && this.auth.user.BranchID) {
      this.UserID = this.auth.user.UserID;
      this.BranchID = this.auth.user.BranchID;

      // console.log(this.UserID, this.BranchID);

    } else {
      this.router.navigateByUrl('auth/login');
    }
  }

  ngOnInit() {
    this.initialization();
    this.getData();
  }

  initialization() {

    this.isSubmitted = false;

    this.transactionType = '1';
    this.customerType = '';
    this.oldCustomer = '';
    this.purpose = '';
    this.country = '';
    this.documentType = '';
    this.documentNumber = '';
    this.suspiciousTransaction = false;
    // this.fcAmount = 0;
    // this.exRate = 0;
    this.total = 0.0;


    this.customerTypesArray = [{ CustomerTypeId: '', CustomerTypeName: 'Select Customer Type' }];
    this.customersArray = [{ RemitterId: '', RemitterFName: 'Select Customer' }];
    this.purposesArray = [];
    this.purposesAllArray = [];
    this.countriesArray = [];
    this.documentTypesArray = [];
    this.currenciesArray = [];
    this.stockArray = [];

    this.customer = {
      remitterId: '',
      name: '',
      contactNo: '',
      address: '',
    };

    this.currency = {
      currencyId: '',
      currencyCode: '',
      currencyName: '',
      BuyHighRate: 0,
      BuyLowRate: 0,
      SellHighRate: 0,
      SellLowRate: 0
    };
  }

  resetCustomerData() {
    this.customer = {
      remitterId: '',
      name: '',
      contactNo: '',
      address: '',
    };
  }

  getData() {
    this.api.get('Vouchers/GetData').then((data: any) => {
      // console.log('Data', data);
      this.customerTypesArray = this.customerTypesArray.concat(data.CustomerTypes);
      this.purposesAllArray = data.Purposes;
      this.countriesArray = data.Countries;
      this.documentTypesArray = data.DocumnetTypes;
      this.currenciesArray = data.Currencies;
      this.stockArray = data.Stock;

      this.isDataLoaded = true;

    });
  }

  getRemitters() {
    if (this.customerType) {

      this._loadPurposes();

      this.oldCustomer = '';

      this.api.get('Remitters/Get?CustomerTypeId=' + this.customerType).then((data: any) => {
        // console.log('Remitters Data', data);

        if (data.length > 0) {
          this.customersArray = [{ RemitterId: '', RemitterFName: 'Select Customer' }];
          this.customersArray = this.customersArray.concat(data);
        } else {
          this.customersArray = [{ RemitterId: null, RemitterFName: 'No Customers' }];
          this.resetCustomerData();
        }

      });
    }
  }

  _loadPurposes() {
    // tslint:disable-next-line:radix
    const customerTypeId = parseInt(this.customerType);
    // console.log('customerTypeId', customerTypeId);

    // tslint:disable-next-line:max-line-length
    this.purposesArray = this.purposesAllArray.filter(f => f.CustomerTypeId === customerTypeId && f.OwnPurpose === true && f.TransStateId === 4);
    // console.log('Pusposes', this.purposesArray);
  }

  setCustomerData() {
    // console.log('oldCustomer', this.oldCustomer);
    if (this.oldCustomer) {
      // tslint:disable-next-line:radix
      const customerData = this.customersArray.filter(f => f.RemitterId === parseInt(this.oldCustomer)).pop();
      // console.log('customerData', customerData);
      this.customer.name = customerData.RemitterFName;
      this.customer.contactNo = customerData.ContactNo;
      this.customer.address = customerData.Address;
    }
  }

  setCurrencyByCode() {
    if (this.currency.currencyCode !== '') {
      const currency = this.currenciesArray.filter(f => f.CurrencyCode === this.currency.currencyCode).pop();
      this._setCurrency(currency);
    }
  }

  setCurrencyById() {
    if (this.currency.currencyId !== '') {
      // tslint:disable-next-line:radix
      const currencyId = parseInt(this.currency.currencyId);
      const currency = this.currenciesArray.filter(f => f.CurrencyId === currencyId).pop();
      this._setCurrency(currency);
    }
  }

  _setCurrency(currency) {
    if (currency) {
      this.currency.currencyId = currency.CurrencyId;
      this.currency.currencyCode = currency.CurrencyCode;
      this.currency.currencyName = currency.FullName;
      this.currency.BuyHighRate = currency.BuyHighRate;
      this.currency.BuyLowRate = currency.BuyLowRate;
      this.currency.SellHighRate = currency.SellHighRate;
      this.currency.SellLowRate = currency.SellLowRate;

      this._getStock();
      this.setMinMax();
    }
  }

  _getStock() {
    if (this.currency.currencyId !== '') {
      // tslint:disable-next-line:radix
      const currencyId = parseInt(this.currency.currencyId);
      const stock = this.stockArray.filter(f => f.Currencyid === currencyId).pop();

      if (stock) {
        this.lcCurrency = stock.BalanceLc;
        this.fcCurrency = stock.BalanceFc;
      }
    }
  }

  setMinMax() {
    if (this.currency.currencyId !== '') {
      if (this.transactionType === '1') {
        this.minRate = this.currency.SellLowRate;
        this.maxRate = this.currency.SellHighRate;
        this.exRate = this.currency.SellHighRate - 0.1;
      }

      if (this.transactionType === '2') {
        this.minRate = this.currency.BuyLowRate;
        this.maxRate = this.currency.BuyHighRate;
        this.exRate = this.currency.BuyHighRate - 0.1;
      }

      this.calculateTotal();
    }
  }

  checkMinMax() {
    if (this.exRate) {
      if (this.exRate < this.minRate || this.exRate > this.maxRate) {
        this.setMinMax();
      }
    }
  }

  calculateTotal() {
    if (this.exRate && this.fcAmount) {
      const total = this.fcAmount * this.exRate;
      this.total = Number((total).toFixed(2));
    }
  }

  submit() {

    this.isSubmitted = true;

    this._check();

    if (this.validation.minMax && this.validation.stock) {
      console.log('Validated!');

      const data = {
        branchId: this.BranchID,
        transTypeId: this.transactionType,
        billTypeid: 1,
        tokenNo: 99,
        customerName: this.customer.name,
        customerAddress: this.customer.address,
        contactNo: this.customer.contactNo,
        docTypeId: this.documentType,
        docNo: this.documentNumber,
        fcAmount: this.fcAmount,
        exRate: this.exRate,
        lcAmount: this.lcCurrency,
        grossLc: this.total,
        roundof: 0,
        netLc: this.total,
        createdBy: this.UserID,
        transStatusId: 3,
        purposeId: this.purpose,
        countryid: this.country,
        currencyId: this.currency.currencyId,
        customerTypeId: this.customerType,
        transNatureId: 2
      };

      console.log('Vlaidated Data', data);

      this._sendSaveRequest(data);

    } else {
      console.log('Invalid Transaction!');
    }

  }

  _check() {
    // MinMax:
    if (this.exRate < this.minRate || this.exRate > this.maxRate) {
      this.validation.minMax = false;
      this.validation.minMaxError = 'ExRate must be between: ' + this.minRate + ' - ' + this.maxRate;
    } else {
      this.validation.minMax = true;
      this.validation.minMaxError = '';
    }

    // Stock:
    const fcAmount = parseFloat(this.fcAmount);
    const fcCurrency = parseFloat(this.fcCurrency);
    if (fcAmount > fcCurrency) {
      this.validation.stock = false;
      this.validation.stockError = 'FcAmount must be less then: ' + this.fcCurrency;
    } else {
      this.validation.stock = true;
      this.validation.stockError = '';
    }
  }

  _sendSaveRequest(data) {
    this.api.post('Vouchers/SaveBillVoucher', data).then((response: any) => {
      // console.log('SaveBillVoucher', response);
      this.helper.successBigToast('Success', 'Successfully created a transaction');

      this.ngOnInit();

    }, () => {
      this.helper.failureBigToast('Failed!', 'Something went wrong, try again later.');
    });
  }

}
