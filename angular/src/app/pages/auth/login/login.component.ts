import { Component, OnInit } from '@angular/core';
import { NgModel } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { HelperService } from 'src/app/services/helper/helper.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  isSubmitted = false;

  constructor(private auth: AuthService, private router: Router, private helper: HelperService) {
    if (this.auth.isLoggedIn) {
      this.router.navigateByUrl('dashboard/cashier');
    }
  }
  ngOnInit() {
  }

  onSubmit(form: NgModel) {
    // console.log(form.value);

    if (form.valid) {

      this.isSubmitted = true;

      this.auth.login(form.value).then((data: any) => {

        this.isSubmitted = false;

        // console.log(data);

        if (data === 'open') {
          this.helper.successToast('Welcome ' + this.auth.user.email, 'Successfully Authenticated!');

          console.log('role', this.auth.user.role);

          if (this.auth.user.role === 0) {
            this.router.navigateByUrl('dashboard/admin');
          } else if (this.auth.user.role === 1) {
            this.router.navigateByUrl('dashboard/cashier');
          } else {
            this.helper.infoToast('Invalid User', 'Contact Administrator!');
          }

        } else if (data === 'locked') {

          this.helper.failureBigToast('Failed!', 'Your account is locked, kindly contact administrator.');

        } else {
          this.helper.failureBigToast('Failed!', 'Incorrect username or password.');
          // this.helper.failureToast('Incorrect username or password', 'Authentication Failed!');
        }

      });
    }
  }

}
