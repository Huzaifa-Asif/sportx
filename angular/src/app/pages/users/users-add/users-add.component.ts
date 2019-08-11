import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { HelperService } from 'src/app/services/helper/helper.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-users-add',
  templateUrl: './users-add.component.html',
  styleUrls: ['./users-add.component.scss']
})
export class UsersAddComponent implements OnInit {

  userForm: FormGroup;

  submitted = false;
  isDataLoaded = false;
  isRequested = true;

  branches = [];
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

    this.getUsersData();

    this.userForm = this.fb.group({
      userId: [this.userId, Validators.required],
      fullName: ['', Validators.required],
      userTypeId: ['', Validators.required],
      branchId: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required],
    });

  }

  get f() { return this.userForm.controls; }

  getUsersData() {
    this.api.get('Users/GetData').then((data: any) => {
      // console.log('Data', data);
      this.branches = data.branches;
      this.types = data.types;
      this.isDataLoaded = true;
    }).catch(err => console.log('Error', err));
  }

  onSubmit() {
    this.submitted = true;

    this._passwordCheck();

    if (this.userForm.valid) {
      this.isRequested = false;
      // console.log('userForm', this.userForm.value);
      this._sendSaveRequest(this.userForm.value);
    }
  }

  _sendSaveRequest(data) {
    this.api.post('Users/Add', data).then((response: any) => {

      this.helper.successBigToast('Success', 'Successfully added!');
      this.ngOnInit();

      this.isRequested = true;

    }, (error: any) => {

      this.isRequested = true;

      if (error.error.Message) {
        if (error.error.Message === 'Already Exists') {
          // tslint:disable-next-line: max-line-length
          this.helper.failureBigToast('Failed!', '"' + data.username + '" is already assigned to another user, kindly user different username for login.');
          return;
        }
      }

      this.helper.failureBigToast('Failed!', 'Invalid data, kindly check all fields.');
    });
  }

  closeMe() {
    this.activeModal.close();
  }

  _passwordCheck() {
    const password = this.userForm.controls['password'].value;
    const confirmPassword = this.userForm.controls['confirmPassword'].value;

    if (password !== confirmPassword) {
      this.userForm.controls['confirmPassword'].setErrors({ NoPassswordMatch: true });
    } else {
      return;
    }
  }

}


