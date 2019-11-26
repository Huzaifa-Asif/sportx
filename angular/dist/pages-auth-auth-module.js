(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["pages-auth-auth-module"],{

/***/ "./src/app/pages/auth/auth-routing.module.ts":
/*!***************************************************!*\
  !*** ./src/app/pages/auth/auth-routing.module.ts ***!
  \***************************************************/
/*! exports provided: AuthRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthRoutingModule", function() { return AuthRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _login_login_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./login/login.component */ "./src/app/pages/auth/login/login.component.ts");
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
                path: 'login',
                component: _login_login_component__WEBPACK_IMPORTED_MODULE_2__["LoginComponent"]
            }
        ]
    }
];
var AuthRoutingModule = /** @class */ (function () {
    function AuthRoutingModule() {
    }
    AuthRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], AuthRoutingModule);
    return AuthRoutingModule;
}());



/***/ }),

/***/ "./src/app/pages/auth/auth.module.ts":
/*!*******************************************!*\
  !*** ./src/app/pages/auth/auth.module.ts ***!
  \*******************************************/
/*! exports provided: AuthModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthModule", function() { return AuthModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _auth_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./auth-routing.module */ "./src/app/pages/auth/auth-routing.module.ts");
/* harmony import */ var _login_login_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./login/login.component */ "./src/app/pages/auth/login/login.component.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};





var AuthModule = /** @class */ (function () {
    function AuthModule() {
    }
    AuthModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            declarations: [_login_login_component__WEBPACK_IMPORTED_MODULE_3__["LoginComponent"]],
            imports: [
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _auth_routing_module__WEBPACK_IMPORTED_MODULE_2__["AuthRoutingModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_4__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_4__["ReactiveFormsModule"]
            ]
        })
    ], AuthModule);
    return AuthModule;
}());



/***/ }),

/***/ "./src/app/pages/auth/login/login.component.html":
/*!*******************************************************!*\
  !*** ./src/app/pages/auth/login/login.component.html ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"container-fluid\">\n  <!--login page start-->\n  <div class=\"authentication-main\">\n    <div class=\"row\">\n      <div class=\"col-md-4 p-0\">\n        <div class=\"auth-innerleft\">\n          <div class=\"text-center\">\n            <img src=\"assets/images/logo-login.png\" class=\"logo-login\" alt=\"\">\n            <hr>\n            <div class=\"social-media\">\n              <ul class=\"list-inline\">\n                <li class=\"list-inline-item\"><i class=\"fa fa-facebook txt-fb\" aria-hidden=\"true\"></i></li>\n                <li class=\"list-inline-item\"><i class=\"fa fa-google-plus txt-google-plus\" aria-hidden=\"true\"></i></li>\n                <li class=\"list-inline-item\"><i class=\"fa fa-twitter txt-twitter\" aria-hidden=\"true\"></i></li>\n                <li class=\"list-inline-item\"><i class=\"fa fa-linkedin txt-linkedin\" aria-hidden=\"true\"></i></li>\n              </ul>\n            </div>\n          </div>\n        </div>\n      </div>\n      <div class=\"col-md-8 p-0\">\n        <div class=\"auth-innerright\">\n          <div class=\"authentication-box\">\n            <h4>LOGIN</h4>\n            <h6>Enter your Email and Password</h6>\n            <div class=\"card mt-4 p-4 mb-0\">\n              <form class=\"theme-form\" #form=\"ngForm\" (ngSubmit)=\"onSubmit(form)\" autocomplete=\"false\">\n                <div class=\"form-group\">\n                  <label class=\"col-form-label pt-0\">Email</label>\n                  <input type=\"text\" name=\"email\" ngModel class=\"form-control form-control-lg\" required>\n                </div>\n                <div class=\"form-group\">\n                  <label class=\"col-form-label\">Password</label>\n                  <input type=\"password\" name=\"password\" ngModel class=\"form-control form-control-lg\" required>\n                </div>\n                <!-- <div class=\"checkbox p-0\">\n                  <input id=\"checkbox1\" type=\"checkbox\">\n                  <label for=\"checkbox1\">Remember me</label>\n                </div> -->\n                <div class=\"form-group form-row mt-3 mb-0\">\n                  <div class=\"col-md-3\">\n                    <button type=\"submit\" class=\"btn btn-secondary\" [disabled]=\"isSubmitted\">LOGIN</button>\n                  </div>\n                </div>\n              </form>\n\n              <!-- Loader -->\n              <div *ngIf=\"isSubmitted\" class=\"loader-box\">\n                <div class=\"loader\">\n                  <div class=\"line\"></div>\n                  <div class=\"line\"></div>\n                  <div class=\"line\"></div>\n                  <div class=\"line\"></div>\n                </div>\n              </div>\n\n            </div>\n          </div>\n        </div>\n      </div>\n    </div>\n  </div>\n  <!--login page end-->\n</div>\n"

/***/ }),

/***/ "./src/app/pages/auth/login/login.component.scss":
/*!*******************************************************!*\
  !*** ./src/app/pages/auth/login/login.component.scss ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3BhZ2VzL2F1dGgvbG9naW4vbG9naW4uY29tcG9uZW50LnNjc3MifQ== */"

/***/ }),

/***/ "./src/app/pages/auth/login/login.component.ts":
/*!*****************************************************!*\
  !*** ./src/app/pages/auth/login/login.component.ts ***!
  \*****************************************************/
/*! exports provided: LoginComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoginComponent", function() { return LoginComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_auth_auth_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/auth/auth.service */ "./src/app/services/auth/auth.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../../services/helper/helper.service */ "./src/app/services/helper/helper.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var LoginComponent = /** @class */ (function () {
    function LoginComponent(auth, router, helper) {
        this.auth = auth;
        this.router = router;
        this.helper = helper;
        this.isSubmitted = false;
        if (this.auth.isLoggedIn) {
            this.router.navigateByUrl('dashboard/cashier');
        }
    }
    LoginComponent.prototype.ngOnInit = function () {
    };
    LoginComponent.prototype.onSubmit = function (form) {
        // console.log(form.value);
        var _this = this;
        if (form.valid) {
            this.isSubmitted = true;
            this.auth.login(form.value).then(function (data) {
                _this.isSubmitted = false;
                // console.log(data);
                if (data === 'open') {
                    _this.helper.successToast('Welcome ' + _this.auth.user.email, 'Successfully Authenticated!');
                    console.log('role', _this.auth.user.role);
                    if (_this.auth.user.role === 0) {
                        _this.router.navigateByUrl('dashboard/admin');
                    }
                    else if (_this.auth.user.role === 1) {
                        _this.router.navigateByUrl('dashboard/cashier');
                    }
                    else {
                        _this.helper.infoToast('Invalid User', 'Contact Administrator!');
                    }
                }
                else if (data === 'locked') {
                    _this.helper.failureBigToast('Failed!', 'Your account is locked, kindly contact administrator.');
                }
                else {
                    _this.helper.failureBigToast('Failed!', 'Incorrect username or password.');
                    // this.helper.failureToast('Incorrect username or password', 'Authentication Failed!');
                }
            });
        }
    };
    LoginComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-login',
            template: __webpack_require__(/*! ./login.component.html */ "./src/app/pages/auth/login/login.component.html"),
            styles: [__webpack_require__(/*! ./login.component.scss */ "./src/app/pages/auth/login/login.component.scss")]
        }),
        __metadata("design:paramtypes", [_services_auth_auth_service__WEBPACK_IMPORTED_MODULE_1__["AuthService"], _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"], _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_3__["HelperService"]])
    ], LoginComponent);
    return LoginComponent;
}());



/***/ })

}]);
//# sourceMappingURL=pages-auth-auth-module.js.map