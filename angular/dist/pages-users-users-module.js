(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["pages-users-users-module"],{

/***/ "./src/app/pages/users/users-add/users-add.component.html":
/*!****************************************************************!*\
  !*** ./src/app/pages/users/users-add/users-add.component.html ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"modal-header\">\n  <h5 class=\"modal-title\" id=\"exampleModalLabel2\">\n    <span *ngIf=\"!isDataLoaded\">Please wait...</span>\n    <span *ngIf=\"isDataLoaded\">Adding User</span></h5>\n  <button type=\"button\" class=\"close\" (click)=\"closeMe()\">\n    <span aria-hidden=\"true\">&times;</span>\n  </button>\n</div>\n<form [formGroup]=\"userForm\" (ngSubmit)=\"onSubmit()\">\n  <div class=\"modal-body\">\n\n    <!-- Loader  -->\n    <div *ngIf=\"!isDataLoaded\" class=\"row justify-content-center\">\n      <div class=\"col-2\">\n        <div class=\"loader-box\">\n          <div class=\"loader\">\n            <div class=\"line\"></div>\n            <div class=\"line\"></div>\n            <div class=\"line\"></div>\n            <div class=\"line\"></div>\n          </div>\n        </div>\n      </div>\n    </div>\n\n    <!-- Records -->\n    <div *ngIf=\"isDataLoaded\">\n\n      <div class=\"row\">\n\n        <!-- Left Section -->\n        <div class=\"col-6\">\n\n          <!-- Full Name -->\n          <div class=\"form-row\">\n            <div class=\"col-12 mb-3\">\n              <label for=\"fullName\">Full Name</label>\n              <input type=\"text\" class=\"form-control input-air-primary\" id=\"fullName\" formControlName=\"fullName\"\n                [ngClass]=\"{ 'is-invalid': submitted && f.fullName.errors }\" autocomplete=\"new-password\">\n\n              <div *ngIf=\"submitted && f['fullName'].errors\" class=\"text text-danger\">\n                <div *ngIf=\"f['fullName'].errors.required\">Required</div>\n              </div>\n            </div>\n          </div>\n\n          <!-- User Type -->\n          <div class=\"form-row\">\n            <div class=\"col-12 mb-3\">\n              <label for=\"userTypeId\">User Type</label>\n              <select class=\"form-control input-air-primary\" id=\"userTypeId\" formControlName=\"userTypeId\"\n                [ngClass]=\"{ 'is-invalid': submitted && f.userTypeId.errors }\">\n                <option value=\"\">Select Type</option>\n                <option *ngFor=\"let type of types\" value=\"{{type.UserTypeId}}\">{{type.UserTypeName}}\n                </option>\n              </select>\n\n              <div *ngIf=\"submitted && f['userTypeId'].errors\" class=\"text text-danger\">\n                <div *ngIf=\"f['userTypeId'].errors.required\">Required</div>\n              </div>\n            </div>\n          </div>\n\n          <!-- Branch -->\n          <div class=\"form-row\">\n            <div class=\"col-12 mb-3\">\n              <label for=\"branchId\">Branch</label>\n              <select class=\"form-control input-air-primary\" id=\"branchId\" formControlName=\"branchId\"\n                [ngClass]=\"{ 'is-invalid': submitted && f.branchId.errors }\">\n                <option value=\"\">Select Region</option>\n                <option *ngFor=\"let branch of branches\" value=\"{{branch.BranchId}}\">{{branch.BranchName}}</option>\n              </select>\n\n              <div *ngIf=\"submitted && f['branchId'].errors\" class=\"text text-danger\">\n                <div *ngIf=\"f['branchId'].errors.required\">Required</div>\n              </div>\n            </div>\n          </div>\n\n        </div>\n\n        <!-- Right Section -->\n        <div class=\"col-6\">\n\n          <!-- Username (Login) -->\n          <div class=\"form-row\">\n            <div class=\"col-12 mb-3\">\n              <label for=\"username\">Username (Login)</label>\n              <input type=\"text\" class=\"form-control input-air-primary\" id=\"username\" formControlName=\"username\"\n                [ngClass]=\"{ 'is-invalid': submitted && f.username.errors }\" autocomplete=\"new-password\">\n\n              <div *ngIf=\"submitted && f['username'].errors\" class=\"text text-danger\">\n                <div *ngIf=\"f['username'].errors.required\">Required</div>\n              </div>\n            </div>\n          </div>\n\n          <!-- Password (Login) -->\n          <div class=\"form-row\">\n            <div class=\"col-12 mb-3\">\n              <label for=\"password\">Password (Login)</label>\n              <input type=\"password\" class=\"form-control input-air-primary\" id=\"password\" formControlName=\"password\"\n                [ngClass]=\"{ 'is-invalid': submitted && f.password.errors }\" autocomplete=\"new-password\">\n\n              <div *ngIf=\"submitted && f['password'].errors\" class=\"text text-danger\">\n                <div *ngIf=\"f['password'].errors.required\">Required</div>\n              </div>\n            </div>\n          </div>\n\n\n          <!-- Confirm Password -->\n          <div class=\"form-row\">\n            <div class=\"col-12 mb-3\">\n              <label for=\"confirmPassword\">Confirm Password</label>\n              <input type=\"password\" class=\"form-control input-air-primary\" id=\"confirmPassword\"\n                formControlName=\"confirmPassword\" [ngClass]=\"{ 'is-invalid': submitted && f.confirmPassword.errors }\">\n\n              <div *ngIf=\"submitted && f['confirmPassword'].errors\" class=\"text text-danger\">\n                <div *ngIf=\"f['confirmPassword'].errors.required\">Required</div>\n                <div *ngIf=\"f['confirmPassword'].hasError('NoPassswordMatch')\">Password do not match</div>\n              </div>\n            </div>\n          </div>\n\n\n        </div>\n\n      </div>\n\n    </div>\n\n  </div>\n  <div *ngIf=\"isDataLoaded\" class=\"modal-footer\">\n\n    <div *ngIf=\"isRequested\">\n      <button type=\"button\" class=\"btn btn-default\" (click)=\"closeMe()\">Close</button>\n      <button type=\"submit\" class=\"btn btn-primary\">Submit</button>\n    </div>\n    <!-- Loader  -->\n    <div *ngIf=\"!isRequested\">\n      <button type=\"button\" class=\"btn btn-default\">Please Wait...</button>\n    </div>\n\n  </div>\n\n</form>\n"

/***/ }),

/***/ "./src/app/pages/users/users-add/users-add.component.scss":
/*!****************************************************************!*\
  !*** ./src/app/pages/users/users-add/users-add.component.scss ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3BhZ2VzL3VzZXJzL3VzZXJzLWFkZC91c2Vycy1hZGQuY29tcG9uZW50LnNjc3MifQ== */"

/***/ }),

/***/ "./src/app/pages/users/users-add/users-add.component.ts":
/*!**************************************************************!*\
  !*** ./src/app/pages/users/users-add/users-add.component.ts ***!
  \**************************************************************/
/*! exports provided: UsersAddComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsersAddComponent", function() { return UsersAddComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _services_api_rest_api_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../services/api/rest-api.service */ "./src/app/services/api/rest-api.service.ts");
/* harmony import */ var _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../services/helper/helper.service */ "./src/app/services/helper/helper.service.ts");
/* harmony import */ var _services_auth_auth_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../../services/auth/auth.service */ "./src/app/services/auth/auth.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/fesm5/ng-bootstrap.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var UsersAddComponent = /** @class */ (function () {
    function UsersAddComponent(fb, api, helper, auth, router, activeModal) {
        this.fb = fb;
        this.api = api;
        this.helper = helper;
        this.auth = auth;
        this.router = router;
        this.activeModal = activeModal;
        this.submitted = false;
        this.isDataLoaded = false;
        this.isRequested = true;
        this.branches = [];
        this.types = [];
        if (this.auth.user.UserID) {
            this.userId = this.auth.user.UserID;
        }
        else {
            this.router.navigateByUrl('auth/login');
        }
    }
    UsersAddComponent.prototype.ngOnInit = function () {
        this.submitted = false;
        this.isDataLoaded = false;
        this.getUsersData();
        this.userForm = this.fb.group({
            userId: [this.userId, _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            fullName: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            userTypeId: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            branchId: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            username: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            password: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            confirmPassword: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
        });
    };
    Object.defineProperty(UsersAddComponent.prototype, "f", {
        get: function () { return this.userForm.controls; },
        enumerable: true,
        configurable: true
    });
    UsersAddComponent.prototype.getUsersData = function () {
        var _this = this;
        this.api.get('Users/GetData').then(function (data) {
            // console.log('Data', data);
            _this.branches = data.branches;
            _this.types = data.types;
            _this.isDataLoaded = true;
        }).catch(function (err) { return console.log('Error', err); });
    };
    UsersAddComponent.prototype.onSubmit = function () {
        this.submitted = true;
        this._passwordCheck();
        if (this.userForm.valid) {
            this.isRequested = false;
            // console.log('userForm', this.userForm.value);
            this._sendSaveRequest(this.userForm.value);
        }
    };
    UsersAddComponent.prototype._sendSaveRequest = function (data) {
        var _this = this;
        this.api.post('Users/Add', data).then(function (response) {
            _this.helper.successBigToast('Success', 'Successfully added!');
            _this.ngOnInit();
            _this.isRequested = true;
        }, function (error) {
            _this.isRequested = true;
            if (error.error.Message) {
                if (error.error.Message === 'Already Exists') {
                    // tslint:disable-next-line: max-line-length
                    _this.helper.failureBigToast('Failed!', '"' + data.username + '" is already assigned to another user, kindly user different username for login.');
                    return;
                }
            }
            _this.helper.failureBigToast('Failed!', 'Invalid data, kindly check all fields.');
        });
    };
    UsersAddComponent.prototype.closeMe = function () {
        this.activeModal.close();
    };
    UsersAddComponent.prototype._passwordCheck = function () {
        var password = this.userForm.controls['password'].value;
        var confirmPassword = this.userForm.controls['confirmPassword'].value;
        if (password !== confirmPassword) {
            this.userForm.controls['confirmPassword'].setErrors({ NoPassswordMatch: true });
        }
        else {
            return;
        }
    };
    UsersAddComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-users-add',
            template: __webpack_require__(/*! ./users-add.component.html */ "./src/app/pages/users/users-add/users-add.component.html"),
            styles: [__webpack_require__(/*! ./users-add.component.scss */ "./src/app/pages/users/users-add/users-add.component.scss")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"], _services_api_rest_api_service__WEBPACK_IMPORTED_MODULE_2__["RestApiService"], _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_3__["HelperService"],
            _services_auth_auth_service__WEBPACK_IMPORTED_MODULE_4__["AuthService"], _angular_router__WEBPACK_IMPORTED_MODULE_5__["Router"], _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_6__["NgbActiveModal"]])
    ], UsersAddComponent);
    return UsersAddComponent;
}());



/***/ }),

/***/ "./src/app/pages/users/users-edit/users-edit.component.html":
/*!******************************************************************!*\
  !*** ./src/app/pages/users/users-edit/users-edit.component.html ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"modal-header\">\n  <h5 class=\"modal-title\" id=\"exampleModalLabel2\">\n    <span *ngIf=\"!isDataLoaded\">Please wait...</span>\n    <span *ngIf=\"isDataLoaded\">Updation {{user.name}} Accout</span></h5>\n  <button type=\"button\" class=\"close\" (click)=\"closeMe()\">\n    <span aria-hidden=\"true\">&times;</span>\n  </button>\n</div>\n<form [formGroup]=\"userForm\" (ngSubmit)=\"onSubmit()\">\n  <div class=\"modal-body\">\n\n    <!-- Loader  -->\n    <div *ngIf=\"!isDataLoaded\" class=\"row justify-content-center\">\n      <div class=\"col-2\">\n        <div class=\"loader-box\">\n          <div class=\"loader\">\n            <div class=\"line\"></div>\n            <div class=\"line\"></div>\n            <div class=\"line\"></div>\n            <div class=\"line\"></div>\n          </div>\n        </div>\n      </div>\n    </div>\n\n    <!-- Records -->\n    <div *ngIf=\"isDataLoaded\">\n\n      <div class=\"row\">\n\n        <!-- Left Section -->\n        <div class=\"col-6\">\n\n          <!-- Full Name -->\n          <div class=\"form-row\">\n            <div class=\"col-12 mb-3\">\n              <label for=\"name\">Full Name</label>\n              <input type=\"text\" class=\"form-control input-air-primary\" id=\"name\" formControlName=\"name\"\n                [ngClass]=\"{ 'is-invalid': submitted && f.name.errors }\" autocomplete=\"new-password\">\n\n              <div *ngIf=\"submitted && f['name'].errors\" class=\"text text-danger\">\n                <div *ngIf=\"f['name'].errors.required\">Required</div>\n              </div>\n            </div>\n          </div>\n\n\n          <!-- Contact Number -->\n          <div class=\"form-row\">\n            <div class=\"col-12 mb-3\">\n              <label for=\"contact\">Contact Number</label>\n              <input type=\"text\" class=\"form-control input-air-primary\" id=\"contact\" formControlName=\"contact\"\n                [ngClass]=\"{ 'is-invalid': submitted && f.contact.errors }\">\n\n              <div *ngIf=\"submitted && f['contact'].errors\" class=\"text text-danger\">\n                <div *ngIf=\"f['contact'].errors.required\">Required</div>\n              </div>\n            </div>\n          </div>\n\n\n\n\n            <!-- Location Lat -->\n            <div class=\"form-row\">\n                <div class=\"col-12 mb-3\">\n                  <label for=\"lat\">Latitude</label>\n                  <input type=\"text\" class=\"form-control input-air-primary\" id=\"lat\" formControlName=\"lat\"\n                    [ngClass]=\"{ 'is-invalid': submitted && f.lat.errors }\" autocomplete=\"new-password\">\n    \n                  <div *ngIf=\"submitted && f['lat'].errors\" class=\"text text-danger\">\n                    <div *ngIf=\"f['lat'].errors.required\">Required</div>\n                  </div>\n                </div>\n              </div>\n\n\n\n\n\n\n\n          <!-- State -->\n\n            <div class=\"form-row\">\n              <div class=\"col-12 mb-3\">\n                <label for=\"state\">State</label>\n                <select class=\"form-control input-air-primary\" id=\"state\" formControlName=\"state\"\n                  [ngClass]=\"{ 'is-invalid': submitted && f.category.errors }\">\n                  <option value=\"\">Select State</option>\n                  <option *ngFor=\"let stat of status\" value=\"{{stat.name}}\">{{stat.name}}\n                  </option>\n                </select>\n  \n                <div *ngIf=\"submitted && f['state'].errors\" class=\"text text-danger\">\n                  <div *ngIf=\"f['state'].errors.required\">Required</div>\n                </div>\n              </div>\n            </div>\n\n        <!-- End Left Section -->\n        </div>\n\n\n\n        <!-- Right Section -->\n        <div class=\"col-6\">\n\n          <!-- Email -->\n          <div class=\"form-row\">\n            <div class=\"col-12 mb-3\">\n              <label for=\"email\">Email (Login)</label>\n              <input type=\"text\" disabled=\"true\" class=\"form-control input-air-primary\" id=\"email\" formControlName=\"email\"\n                [ngClass]=\"{ 'is-invalid': submitted && f.email.errors }\" autocomplete=\"new-password\">\n\n              <div *ngIf=\"submitted && f['email'].errors\" class=\"text text-danger\">\n                <div *ngIf=\"f['email'].errors.required\">Required</div>\n              </div>\n            </div>\n          </div>\n\n\n\n          <!-- Category -->\n          <div class=\"form-row\">\n              <div class=\"col-12 mb-3\">\n                <label for=\"category\">Category</label>\n                <select class=\"form-control input-air-primary\" id=\"category\" formControlName=\"category\"\n                  [ngClass]=\"{ 'is-invalid': submitted && f.category.errors }\">\n                  <option value=\"\">Select Category</option>\n                  <option *ngFor=\"let service of services\" value=\"{{service.name}}\">{{service.name}}\n                  </option>\n                </select>\n  \n                <div *ngIf=\"submitted && f['category'].errors\" class=\"text text-danger\">\n                  <div *ngIf=\"f['category'].errors.required\">Required</div>\n                </div>\n              </div>\n            </div>\n  \n  \n  \n            <!-- Location Long -->\n            <div class=\"form-row\">\n              <div class=\"col-12 mb-3\">\n                <label for=\"long\">Longitude</label>\n                <input type=\"text\" class=\"form-control input-air-primary\" id=\"long\" formControlName=\"long\"\n                  [ngClass]=\"{ 'is-invalid': submitted && f.long.errors }\" autocomplete=\"new-password\">\n  \n                <div *ngIf=\"submitted && f['long'].errors\" class=\"text text-danger\">\n                  <div *ngIf=\"f['long'].errors.required\">Required</div>\n                </div>\n              </div>\n            </div>\n\n              <!-- Address -->\n          <div class=\"form-row\">\n              <div class=\"col-12 mb-3\">\n                <label for=\"address\">Address</label>\n                \n                    <textarea type=\"text\" id=\"address\" class=\"md-textarea md-textarea-auto form-control\" formControlName=\"address\"  mdbInput \n                    [ngClass]=\"{ 'is-invalid': submitted && f.address.errors }\"></textarea>\n  \n  \n                <div *ngIf=\"submitted && f['address'].errors\" class=\"text text-danger\">\n                  <div *ngIf=\"f['address'].errors.required\">Required</div>\n                </div>\n              </div>\n            </div>\n\n\n        <!--End Right Section -->\n        </div>\n\n      <!-- Row Ended-->\n      </div>\n\n      <hr>\n      <!-- 2nd Row Start-->\n      <div class=\"row\">\n        \n          <div class=\"col-6\">\n                  <!-- Password (Login) -->\n                  <div class=\"form-row\">\n                    <div class=\"col-12 mb-3\">\n                      <label for=\"newPassword\">New Password</label>\n                      <input type=\"password\" class=\"form-control input-air-primary\" id=\"newPassword\"\n                        formControlName=\"newPassword\" [ngClass]=\"{ 'is-invalid': submitted && f.newPassword.errors }\"\n                        autocomplete=\"new-password\">\n        \n                      <div *ngIf=\"submitted && f['newPassword'].errors\" class=\"text text-danger\">\n                        <div *ngIf=\"f['newPassword'].errors.required\">Required</div>\n                      </div>\n                    </div>\n                  </div>\n        \n          </div>\n      \n          <div class=\"col-6\">\n                  <!-- Password (Login) -->\n                  <div class=\"form-row\">\n                    <div class=\"col-12 mb-3\">\n                      <label for=\"confirmPassword\">Confirm Password</label>\n                      <input type=\"password\" class=\"form-control input-air-primary\" id=\"confirmPassword\"\n                        formControlName=\"confirmPassword\" [ngClass]=\"{ 'is-invalid': submitted && f.confirmPassword.errors }\">\n        \n                      <div *ngIf=\"submitted && f['confirmPassword'].errors\" class=\"text text-danger\">\n                        <div *ngIf=\"f['confirmPassword'].errors.required\">Required</div>\n                        <div *ngIf=\"f['confirmPassword'].hasError('NoPassswordMatch')\">Password do not match</div>\n                      </div>\n                    </div>\n                  </div>\n      \n      \n           </div>  \n       <!--2nd Row Ended-->          \n      </div>\n\n\n    </div>\n\n  </div>\n  <div *ngIf=\"isDataLoaded\" class=\"modal-footer\">\n\n    <div *ngIf=\"isRequested\">\n      <button type=\"button\" class=\"btn btn-default\" (click)=\"closeMe()\">Close</button>\n      <button type=\"submit\" class=\"btn btn-primary\">Submit</button>\n    </div>\n    <!-- Loader  -->\n    <div *ngIf=\"!isRequested\">\n      <button type=\"button\" class=\"btn btn-default\">Please Wait...</button>\n    </div>\n\n  </div>\n\n</form>\n"

/***/ }),

/***/ "./src/app/pages/users/users-edit/users-edit.component.scss":
/*!******************************************************************!*\
  !*** ./src/app/pages/users/users-edit/users-edit.component.scss ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3BhZ2VzL3VzZXJzL3VzZXJzLWVkaXQvdXNlcnMtZWRpdC5jb21wb25lbnQuc2NzcyJ9 */"

/***/ }),

/***/ "./src/app/pages/users/users-edit/users-edit.component.ts":
/*!****************************************************************!*\
  !*** ./src/app/pages/users/users-edit/users-edit.component.ts ***!
  \****************************************************************/
/*! exports provided: UsersEditComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsersEditComponent", function() { return UsersEditComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _services_api_rest_api_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../services/api/rest-api.service */ "./src/app/services/api/rest-api.service.ts");
/* harmony import */ var _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../services/helper/helper.service */ "./src/app/services/helper/helper.service.ts");
/* harmony import */ var _services_auth_auth_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../../services/auth/auth.service */ "./src/app/services/auth/auth.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/fesm5/ng-bootstrap.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var UsersEditComponent = /** @class */ (function () {
    function UsersEditComponent(fb, api, helper, auth, router, activeModal) {
        this.fb = fb;
        this.api = api;
        this.helper = helper;
        this.auth = auth;
        this.router = router;
        this.activeModal = activeModal;
        this.submitted = false;
        this.isDataLoaded = false;
        this.isRequested = true;
        this.branches = [];
        this.services = [];
        this.status = [];
        this.isPasswordValidated = true;
        if (this.auth.user.email) {
            this.email = this.auth.user.email;
        }
        else {
            this.router.navigateByUrl('auth/login');
        }
    }
    UsersEditComponent.prototype.ngOnInit = function () {
        this.submitted = false;
        this.isDataLoaded = false;
        this.status = [
            { name: "pending" },
            { name: "approved" },
            { name: "blocked" }
        ];
        this.getUsersData();
        this.userForm = this.fb.group({
            name: [this.user.name, _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            contact: [this.user.contact, _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            category: [this.user.category, _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            address: [this.user.address, _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            state: [this.user.state.toString(), _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            email: [this.user.email, _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            lat: [this.user.location.lat, _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            long: [this.user.location.long, _angular_forms__WEBPACK_IMPORTED_MODULE_1__["Validators"].required],
            newPassword: [''],
            confirmPassword: [''],
        });
    };
    Object.defineProperty(UsersEditComponent.prototype, "f", {
        get: function () { return this.userForm.controls; },
        enumerable: true,
        configurable: true
    });
    UsersEditComponent.prototype.getUsersData = function () {
        var _this = this;
        this.api.get('servicecategory/get_serviceCategory').then(function (data) {
            // console.log('Data', data);
            var i = 0;
            _this.services = data;
            // for(i;i<data.length;i++)
            // {
            //   this.categories[i] = data[i].name;
            //   // this.services[i] = data[i].name;
            //   // console.log("service",data[i].name)
            // }
            // console.log("All services",this.services)
            _this.isDataLoaded = true;
        }).catch(function (err) { return console.log('Error', err); });
    };
    UsersEditComponent.prototype.closeMe = function () {
        this.activeModal.close();
    };
    UsersEditComponent.prototype.onSubmit = function () {
        this.submitted = true;
        // console.log('Form Values', this.userForm.value);
        if (this.userForm.valid) {
            this._passwordCheck();
            if (this.isPasswordValidated) {
                this.isRequested = false;
                var userName = this.user.name;
                var username = this.userForm.controls['name'].value;
                this._sendUpdateRequest(this.userForm.value, userName, username);
            }
        }
    };
    UsersEditComponent.prototype._sendUpdateRequest = function (data, userName, username) {
        var _this = this;
        console.log("Req", data);
        this.api.patch('serviceProvider/update_serviceProvider/', data.email, data).then(function (response) {
            _this.isRequested = true;
            _this.helper.successBigToast('Success', 'Successfully updated: ' + userName + '\'s Account');
            setTimeout(function () {
                window.location.reload();
            }, 3000);
        }, function (error) {
            _this.isRequested = true;
            if (error.error.Message) {
                if (error.error.Message === 'Already Exists') {
                    // tslint:disable-next-line: max-line-length
                    _this.helper.failureBigToast('Failed!', '"' + username + '" is already assigned to another user, kindly user different username for login.');
                    return;
                }
            }
            _this.helper.failureBigToast('Failed!', 'Invalid data, kindly check updated data.');
        });
    };
    UsersEditComponent.prototype._passwordCheck = function () {
        var password = this.userForm.controls['newPassword'].value;
        var confirmPassword = this.userForm.controls['confirmPassword'].value;
        if (password !== '') {
            if (password !== confirmPassword) {
                this.userForm.controls['confirmPassword'].setErrors({ NoPassswordMatch: true });
                this.isPasswordValidated = false;
            }
        }
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])(),
        __metadata("design:type", Object)
    ], UsersEditComponent.prototype, "user", void 0);
    UsersEditComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-users-edit',
            template: __webpack_require__(/*! ./users-edit.component.html */ "./src/app/pages/users/users-edit/users-edit.component.html"),
            styles: [__webpack_require__(/*! ./users-edit.component.scss */ "./src/app/pages/users/users-edit/users-edit.component.scss")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"], _services_api_rest_api_service__WEBPACK_IMPORTED_MODULE_2__["RestApiService"], _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_3__["HelperService"],
            _services_auth_auth_service__WEBPACK_IMPORTED_MODULE_4__["AuthService"], _angular_router__WEBPACK_IMPORTED_MODULE_5__["Router"], _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_6__["NgbActiveModal"]])
    ], UsersEditComponent);
    return UsersEditComponent;
}());



/***/ }),

/***/ "./src/app/pages/users/users-list/users-list.component.html":
/*!******************************************************************!*\
  !*** ./src/app/pages/users/users-list/users-list.component.html ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!-- Container-fluid starts -->\n<div class=\"container-fluid\">\n  <div class=\"page-header\">\n    <div class=\"row\">\n      <div class=\"col-lg-6\">\n        <h3>Service Providers\n          <!-- <small>Universal Admin Panel</small> -->\n        </h3>\n      </div>\n      <!-- <div class=\"col-lg-6 text-right\">\n        <button type=\"button\" class=\"btn btn-pill btn-outline-primary btn-sm\" (click)=\"openAddModal()\">Add New</button>\n      </div> -->\n    </div>\n  </div>\n</div>\n<!-- Container-fluid Ends -->\n<!-- Container-fluid starts -->\n<div class=\"container-fluid\">\n  <div class=\"row\">\n    <div class=\"col-sm-12\">\n      <div class=\"card\">\n        <div class=\"card-body\">\n\n          <!-- Loader  -->\n          <div *ngIf=\"!isDataLoaded\" class=\"row justify-content-center\">\n            <div class=\"col-2\">\n              <div class=\"loader-box\">\n                <div class=\"loader\">\n                  <div class=\"line\"></div>\n                  <div class=\"line\"></div>\n                  <div class=\"line\"></div>\n                  <div class=\"line\"></div>\n                </div>\n              </div>\n            </div>\n          </div>\n\n          <!-- Records -->\n          <div *ngIf=\"isDataLoaded\">\n\n            <div *ngIf=\"users.length > 0\" class=\"table-responsive\">\n\n              <table datatable [dtOptions]=\"dtOptions\" class=\"row-border hover\"></table>\n\n              <!-- <table *ngIf=\"users.length > 0\" datatable class=\"row-border hover\">\n                <thead>\n                  <tr>\n                    <th>User Name</th>\n                    <th>Type</th>\n                    <th>Branch</th>\n                    <th>Status</th>\n                  </tr>\n                </thead>\n                <tbody>\n                  <tr *ngFor=\"let u of users\" (click)=\"selectedUser(u)\">\n                    <td title=\"{{u.UserId}}\">{{u.UserName}}</td>\n                    <td>{{u.UserTypeName}}</td>\n                    <td>{{u.BranchName}}</td>\n                    <td>\n                      <span *ngIf=\"!u.Locked\" class=\"badge badge-pill badge-success\">Active</span>\n                      <span *ngIf=\"u.Locked\" class=\"badge badge-pill badge-danger\">Blocked</span>\n                    </td>\n                  </tr>\n                </tbody>\n              </table> -->\n\n            </div>\n\n          </div>\n\n        </div>\n      </div>\n    </div>\n  </div>\n\n</div>\n<!-- Container-fluid Ends -->\n"

/***/ }),

/***/ "./src/app/pages/users/users-list/users-list.component.scss":
/*!******************************************************************!*\
  !*** ./src/app/pages/users/users-list/users-list.component.scss ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3BhZ2VzL3VzZXJzL3VzZXJzLWxpc3QvdXNlcnMtbGlzdC5jb21wb25lbnQuc2NzcyJ9 */"

/***/ }),

/***/ "./src/app/pages/users/users-list/users-list.component.ts":
/*!****************************************************************!*\
  !*** ./src/app/pages/users/users-list/users-list.component.ts ***!
  \****************************************************************/
/*! exports provided: UsersListComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsersListComponent", function() { return UsersListComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_api_rest_api_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/api/rest-api.service */ "./src/app/services/api/rest-api.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../services/helper/helper.service */ "./src/app/services/helper/helper.service.ts");
/* harmony import */ var _users_add_users_add_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../users-add/users-add.component */ "./src/app/pages/users/users-add/users-add.component.ts");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/fesm5/ng-bootstrap.js");
/* harmony import */ var _users_edit_users_edit_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../users-edit/users-edit.component */ "./src/app/pages/users/users-edit/users-edit.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var UsersListComponent = /** @class */ (function () {
    function UsersListComponent(api, router, helper, modalService) {
        this.api = api;
        this.router = router;
        this.helper = helper;
        this.modalService = modalService;
        this.isDataLoaded = false;
        this.users = [];
        this.dtOptions = {};
    }
    UsersListComponent.prototype.ngOnInit = function () {
        console.log("ngOnInit called");
        this.getUsers();
    };
    UsersListComponent.prototype.getUsers = function () {
        var _this = this;
        this.api.get('serviceProvider/get_serviceProvider').then(function (response) {
            _this.users = response;
            _this.dtOptions = {
                data: _this.users,
                columns: [{
                        title: 'Name',
                        data: 'name',
                        render: function (data, type, row) {
                            return '<span title="' + row.email + '">' + data + '</span>';
                        }
                    },
                    {
                        title: 'Address',
                        data: 'address'
                    },
                    {
                        title: 'Category',
                        data: 'category'
                    },
                    {
                        title: 'State',
                        data: 'state',
                        render: function (data, type, row) {
                            if (data === "approved") {
                                return '<span class="badge badge-pill badge-success">Approved</span>';
                            }
                            else if (data === "pending") {
                                return '<span class="badge badge-pill badge-warning">Pending</span>';
                            }
                            else if (data === "blocked") {
                                return '<span class="badge badge-pill badge-danger">Blocked</span>';
                            }
                        }
                    }],
                rowCallback: function (row, data, index) {
                    var self = _this;
                    // tslint:disable-next-line: deprecation
                    $('td', row).unbind('click');
                    // tslint:disable-next-line: deprecation
                    $('td', row).bind('click', function () {
                        self.selectedUser(data);
                    });
                    return row;
                }
            };
            _this.isDataLoaded = true;
        }).catch(function (err) { return console.log('Error', err); });
    };
    UsersListComponent.prototype.openAddModal = function () {
        var _this = this;
        var modalRef = this.modalService.open(_users_add_users_add_component__WEBPACK_IMPORTED_MODULE_4__["UsersAddComponent"]);
        modalRef.result.then(function () { _this.ngOnInit(); }, function () { _this.ngOnInit(); });
    };
    UsersListComponent.prototype.selectedUser = function (user) {
        this._openEditModal(user);
    };
    UsersListComponent.prototype._openEditModal = function (user) {
        var modalRef = this.modalService.open(_users_edit_users_edit_component__WEBPACK_IMPORTED_MODULE_6__["UsersEditComponent"]);
        modalRef.componentInstance.user = user;
        // modalRef.result.then(() => { window.location.reload() }, () => { window.location.reload()});
        // modalRef.result.then(() => { this.ngOnInit(); }, () => { this.ngOnInit(); });
    };
    UsersListComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-users-list',
            template: __webpack_require__(/*! ./users-list.component.html */ "./src/app/pages/users/users-list/users-list.component.html"),
            styles: [__webpack_require__(/*! ./users-list.component.scss */ "./src/app/pages/users/users-list/users-list.component.scss")]
        }),
        __metadata("design:paramtypes", [_services_api_rest_api_service__WEBPACK_IMPORTED_MODULE_1__["RestApiService"], _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"], _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_3__["HelperService"], _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_5__["NgbModal"]])
    ], UsersListComponent);
    return UsersListComponent;
}());



/***/ }),

/***/ "./src/app/pages/users/users-routing.module.ts":
/*!*****************************************************!*\
  !*** ./src/app/pages/users/users-routing.module.ts ***!
  \*****************************************************/
/*! exports provided: UsersRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsersRoutingModule", function() { return UsersRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _users_list_users_list_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./users-list/users-list.component */ "./src/app/pages/users/users-list/users-list.component.ts");
/* harmony import */ var _guards_admin_admin_guard__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../guards/admin/admin.guard */ "./src/app/guards/admin/admin.guard.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




var routes = [
    {
        path: '',
        children: [
            {
                path: 'list',
                component: _users_list_users_list_component__WEBPACK_IMPORTED_MODULE_2__["UsersListComponent"],
                canActivate: [_guards_admin_admin_guard__WEBPACK_IMPORTED_MODULE_3__["AdminGuard"]]
            }
        ]
    }
];
var UsersRoutingModule = /** @class */ (function () {
    function UsersRoutingModule() {
    }
    UsersRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], UsersRoutingModule);
    return UsersRoutingModule;
}());



/***/ }),

/***/ "./src/app/pages/users/users.module.ts":
/*!*********************************************!*\
  !*** ./src/app/pages/users/users.module.ts ***!
  \*********************************************/
/*! exports provided: UsersModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsersModule", function() { return UsersModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var angular_datatables__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! angular-datatables */ "./node_modules/angular-datatables/index.js");
/* harmony import */ var _users_routing_module__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./users-routing.module */ "./src/app/pages/users/users-routing.module.ts");
/* harmony import */ var _users_list_users_list_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./users-list/users-list.component */ "./src/app/pages/users/users-list/users-list.component.ts");
/* harmony import */ var _users_add_users_add_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./users-add/users-add.component */ "./src/app/pages/users/users-add/users-add.component.ts");
/* harmony import */ var _users_edit_users_edit_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./users-edit/users-edit.component */ "./src/app/pages/users/users-edit/users-edit.component.ts");
/* harmony import */ var _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @ng-bootstrap/ng-bootstrap */ "./node_modules/@ng-bootstrap/ng-bootstrap/fesm5/ng-bootstrap.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};









var UsersModule = /** @class */ (function () {
    function UsersModule() {
    }
    UsersModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            declarations: [_users_list_users_list_component__WEBPACK_IMPORTED_MODULE_4__["UsersListComponent"], _users_add_users_add_component__WEBPACK_IMPORTED_MODULE_5__["UsersAddComponent"], _users_edit_users_edit_component__WEBPACK_IMPORTED_MODULE_6__["UsersEditComponent"]],
            imports: [
                angular_datatables__WEBPACK_IMPORTED_MODULE_2__["DataTablesModule"],
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _ng_bootstrap_ng_bootstrap__WEBPACK_IMPORTED_MODULE_7__["NgbModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_8__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_8__["ReactiveFormsModule"],
                _users_routing_module__WEBPACK_IMPORTED_MODULE_3__["UsersRoutingModule"]
            ],
            entryComponents: [_users_add_users_add_component__WEBPACK_IMPORTED_MODULE_5__["UsersAddComponent"], _users_edit_users_edit_component__WEBPACK_IMPORTED_MODULE_6__["UsersEditComponent"]]
        })
    ], UsersModule);
    return UsersModule;
}());



/***/ })

}]);
//# sourceMappingURL=pages-users-users-module.js.map