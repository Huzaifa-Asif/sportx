(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["pages-reports-reports-module"],{

/***/ "./src/app/pages/reports/reports-routing.module.ts":
/*!*********************************************************!*\
  !*** ./src/app/pages/reports/reports-routing.module.ts ***!
  \*********************************************************/
/*! exports provided: ReportsRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ReportsRoutingModule", function() { return ReportsRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _service_provider_booking_wise_booking_wise_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./service-provider/booking-wise/booking-wise.component */ "./src/app/pages/reports/service-provider/booking-wise/booking-wise.component.ts");
/* harmony import */ var _service_provider_rating_wise_rating_wise_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./service-provider/rating-wise/rating-wise.component */ "./src/app/pages/reports/service-provider/rating-wise/rating-wise.component.ts");
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
                path: 'booking_wise',
                component: _service_provider_booking_wise_booking_wise_component__WEBPACK_IMPORTED_MODULE_2__["BookingWise"]
            },
            {
                path: 'rating_wise',
                component: _service_provider_rating_wise_rating_wise_component__WEBPACK_IMPORTED_MODULE_3__["RatingWise"]
            },
        ]
    }
];
var ReportsRoutingModule = /** @class */ (function () {
    function ReportsRoutingModule() {
    }
    ReportsRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forChild(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], ReportsRoutingModule);
    return ReportsRoutingModule;
}());



/***/ }),

/***/ "./src/app/pages/reports/reports.module.ts":
/*!*************************************************!*\
  !*** ./src/app/pages/reports/reports.module.ts ***!
  \*************************************************/
/*! exports provided: ReportsModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ReportsModule", function() { return ReportsModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_common__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/common */ "./node_modules/@angular/common/fesm5/common.js");
/* harmony import */ var _reports_routing_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./reports-routing.module */ "./src/app/pages/reports/reports-routing.module.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _service_provider_booking_wise_booking_wise_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./service-provider/booking-wise/booking-wise.component */ "./src/app/pages/reports/service-provider/booking-wise/booking-wise.component.ts");
/* harmony import */ var _service_provider_rating_wise_rating_wise_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./service-provider/rating-wise/rating-wise.component */ "./src/app/pages/reports/service-provider/rating-wise/rating-wise.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};






var ReportsModule = /** @class */ (function () {
    function ReportsModule() {
    }
    ReportsModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            // tslint:disable-next-line: max-line-length
            declarations: [_service_provider_booking_wise_booking_wise_component__WEBPACK_IMPORTED_MODULE_4__["BookingWise"], _service_provider_rating_wise_rating_wise_component__WEBPACK_IMPORTED_MODULE_5__["RatingWise"]],
            imports: [
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormsModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_3__["ReactiveFormsModule"],
                _angular_common__WEBPACK_IMPORTED_MODULE_1__["CommonModule"],
                _reports_routing_module__WEBPACK_IMPORTED_MODULE_2__["ReportsRoutingModule"]
            ]
        })
    ], ReportsModule);
    return ReportsModule;
}());



/***/ }),

/***/ "./src/app/pages/reports/service-provider/booking-wise/booking-wise.component.html":
/*!*****************************************************************************************!*\
  !*** ./src/app/pages/reports/service-provider/booking-wise/booking-wise.component.html ***!
  \*****************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!-- Container-fluid starts -->\n<div class=\"container-fluid\">\n  <div class=\"page-header\">\n    <div class=\"row\">\n      <div class=\"col-lg-6\">\n        <h3>Service Provider (Booking Wise)\n          <small>Report</small>\n        </h3>\n      </div>\n    </div>\n  </div>\n</div>\n<!-- Container-fluid Ends -->\n<iframe style=\"border: none;border-radius: 2px;box-shadow: 0 2px 10px 0 rgba(70, 76, 79, .2);\" width=\"100%\" height=\"480\" src=\"https://charts.mongodb.com/charts-project-0-avtkw/embed/charts?id=e9596d8d-63a8-4b17-b726-f63c65b467c4&tenant=f5d189e6-b0ab-44b0-aea0-a9190f6934f5\"></iframe>\n\n\n\n  <iframe style=\"border: none;border-radius: 2px;box-shadow: 0 2px 10px 0 rgba(70, 76, 79, .2);\" width=\"55%\" height=\"480\" src=\"https://charts.mongodb.com/charts-project-0-avtkw/embed/charts?id=9b89a391-fbe9-4479-a6d7-b0fed9277987&tenant=f5d189e6-b0ab-44b0-aea0-a9190f6934f5\"></iframe>\n\n  \n\n  <iframe style=\" margin-left:1.5%; border: none;border-radius: 2px;box-shadow: 0 2px 10px 0 rgba(70, 76, 79, .2);\" width=\"43.5%\" height=\"480\" src=\"https://charts.mongodb.com/charts-project-0-avtkw/embed/charts?id=0631b3d5-b77a-4a24-83c6-69cb03c00ef0&tenant=f5d189e6-b0ab-44b0-aea0-a9190f6934f5\"></iframe>\n\n\n\n\n\n\n"

/***/ }),

/***/ "./src/app/pages/reports/service-provider/booking-wise/booking-wise.component.scss":
/*!*****************************************************************************************!*\
  !*** ./src/app/pages/reports/service-provider/booking-wise/booking-wise.component.scss ***!
  \*****************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3BhZ2VzL3JlcG9ydHMvc2VydmljZS1wcm92aWRlci9ib29raW5nLXdpc2UvYm9va2luZy13aXNlLmNvbXBvbmVudC5zY3NzIn0= */"

/***/ }),

/***/ "./src/app/pages/reports/service-provider/booking-wise/booking-wise.component.ts":
/*!***************************************************************************************!*\
  !*** ./src/app/pages/reports/service-provider/booking-wise/booking-wise.component.ts ***!
  \***************************************************************************************/
/*! exports provided: BookingWise */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "BookingWise", function() { return BookingWise; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_api_rest_api_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../../services/api/rest-api.service */ "./src/app/services/api/rest-api.service.ts");
/* harmony import */ var _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../services/helper/helper.service */ "./src/app/services/helper/helper.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var BookingWise = /** @class */ (function () {
    function BookingWise(api, helper) {
        this.api = api;
        this.helper = helper;
    }
    BookingWise.prototype.ngOnInit = function () {
    };
    BookingWise = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-booking-wise',
            template: __webpack_require__(/*! ./booking-wise.component.html */ "./src/app/pages/reports/service-provider/booking-wise/booking-wise.component.html"),
            styles: [__webpack_require__(/*! ./booking-wise.component.scss */ "./src/app/pages/reports/service-provider/booking-wise/booking-wise.component.scss")]
        }),
        __metadata("design:paramtypes", [_services_api_rest_api_service__WEBPACK_IMPORTED_MODULE_1__["RestApiService"], _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_2__["HelperService"]])
    ], BookingWise);
    return BookingWise;
}());



/***/ }),

/***/ "./src/app/pages/reports/service-provider/rating-wise/rating-wise.component.html":
/*!***************************************************************************************!*\
  !*** ./src/app/pages/reports/service-provider/rating-wise/rating-wise.component.html ***!
  \***************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!-- Container-fluid starts -->\n<div class=\"container-fluid\">\n  <div class=\"page-header\">\n    <div class=\"row\">\n      <div class=\"col-lg-6\">\n        <h3>Service Provider (Rating Wise)\n          <small>Report</small>\n        </h3>\n      </div>\n    </div>\n  </div>\n</div>\n<!-- Container-fluid Ends -->\n<iframe style=\"border: none;border-radius: 2px;box-shadow: 0 2px 10px 0 rgba(70, 76, 79, .2);\" width=\"100%\" height=\"480\" src=\"https://charts.mongodb.com/charts-project-0-avtkw/embed/charts?id=1df01bb9-79fb-414a-8676-5de489445bdf&tenant=f5d189e6-b0ab-44b0-aea0-a9190f6934f5\"></iframe>\n\n<iframe style=\"border: none;border-radius: 2px;box-shadow: 0 2px 10px 0 rgba(70, 76, 79, .2);\" width=\"44%\" height=\"480\" src=\"https://charts.mongodb.com/charts-project-0-avtkw/embed/charts?id=94719c23-8b1f-4afc-965b-b4c14aea697f&tenant=f5d189e6-b0ab-44b0-aea0-a9190f6934f5\"></iframe>\n\n<iframe style=\" margin-left:1.5%; border: none;border-radius: 2px;box-shadow: 0 2px 10px 0 rgba(70, 76, 79, .2);\" width=\"54.5%\" height=\"480\" src=\"https://charts.mongodb.com/charts-project-0-avtkw/embed/charts?id=33fe903b-4f7f-4f01-ab83-3e72b2a54d44&tenant=f5d189e6-b0ab-44b0-aea0-a9190f6934f5\"></iframe>"

/***/ }),

/***/ "./src/app/pages/reports/service-provider/rating-wise/rating-wise.component.scss":
/*!***************************************************************************************!*\
  !*** ./src/app/pages/reports/service-provider/rating-wise/rating-wise.component.scss ***!
  \***************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL3BhZ2VzL3JlcG9ydHMvc2VydmljZS1wcm92aWRlci9yYXRpbmctd2lzZS9yYXRpbmctd2lzZS5jb21wb25lbnQuc2NzcyJ9 */"

/***/ }),

/***/ "./src/app/pages/reports/service-provider/rating-wise/rating-wise.component.ts":
/*!*************************************************************************************!*\
  !*** ./src/app/pages/reports/service-provider/rating-wise/rating-wise.component.ts ***!
  \*************************************************************************************/
/*! exports provided: RatingWise */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "RatingWise", function() { return RatingWise; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_api_rest_api_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../../services/api/rest-api.service */ "./src/app/services/api/rest-api.service.ts");
/* harmony import */ var _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../services/helper/helper.service */ "./src/app/services/helper/helper.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var RatingWise = /** @class */ (function () {
    function RatingWise(api, helper) {
        this.api = api;
        this.helper = helper;
    }
    RatingWise.prototype.ngOnInit = function () {
    };
    RatingWise = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-rating-wise',
            template: __webpack_require__(/*! ./rating-wise.component.html */ "./src/app/pages/reports/service-provider/rating-wise/rating-wise.component.html"),
            styles: [__webpack_require__(/*! ./rating-wise.component.scss */ "./src/app/pages/reports/service-provider/rating-wise/rating-wise.component.scss")]
        }),
        __metadata("design:paramtypes", [_services_api_rest_api_service__WEBPACK_IMPORTED_MODULE_1__["RestApiService"], _services_helper_helper_service__WEBPACK_IMPORTED_MODULE_2__["HelperService"]])
    ], RatingWise);
    return RatingWise;
}());



/***/ })

}]);
//# sourceMappingURL=pages-reports-reports-module.js.map