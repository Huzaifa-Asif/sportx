import { Injectable } from '@angular/core';
import { RestApiService } from '../api/rest-api.service';
import { Router } from '@angular/router';
import { StorageService } from '../storage/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  isLoggedIn = false;
  user = undefined;

  isAdmin = false;
  isCashier = false;

  // user = {
  //   Locked: null,
  //   UserID: null,
  //   UserName: null,
  //   BranchID: null,
  //   UserType: null,
  //   AccAuthLimit: null,
  //   BillAuthLimit: null,
  //   CashierAccount: null,
  //   Access: {
  //     AllowInsert: null,
  //     AllowUpdate: null,
  //     AllowDelete: null,
  //     AllowPrint: null,
  //     AllowReverse: null
  //   }
  // };

  constructor(private api: RestApiService, private router: Router, private storage: StorageService) {
    // console.log('SavedUserDetails', this.storage.userDetails);
    if (this.storage.userDetails) {
      this.isLoggedIn = true;
      this.user = this.storage.userDetails;

      // Role Check:
      this.roleCheck();
    }
  }

  login(userDeatils) {
    return new Promise((resolve, reject) => {
      this.api.post('loginAdmin', userDeatils).then((data: any) => {
        // console.log('UserData', data);

        if (!data.Locked) {

          // Creating Session:
          this.storage.saveUserDetails(data);
          this.isLoggedIn = true;
          this.user = data;

          // Role Check:
          this.roleCheck();

          resolve('open');

        } else {
          resolve('locked');
        }


      }, () => {
        resolve(false);
      });
    });
  }

  logout() {
    // Reset:
    this.isLoggedIn = false;
    this.isAdmin = false;
    this.isCashier = false;
    this.user = undefined;
    this.storage.removeUserDetails();

    // Redirect:
    this.router.navigate(['/auth/login']);
  }

  roleCheck() {
    if (this.user.role === 0) {
      this.isAdmin = true;
      this.isCashier = false;
    } else {
      this.isAdmin = false;
      this.isCashier = true;
    }
  }
}
