import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { HelperService } from 'src/app/services/helper/helper.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-users-edit',
  templateUrl: './users-edit.component.html',
  styleUrls: ['./users-edit.component.scss']
})
export class UsersEditComponent implements OnInit {

  submitted = false;
  isDataLoaded = false;
  isRequested = true;

  userId;
  userForm: FormGroup;

  branches = [];
  types = [];

  isPasswordValidated = true;

  @Input() user;

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
      updaterUserId: [this.userId, Validators.required],
      userId: [this.user.UserRecId, Validators.required],
      fullName: [this.user.UserName, Validators.required],
      userTypeId: [this.user.UserTypeId, Validators.required],
      branchId: [this.user.BranchId, Validators.required],
      locked: [this.user.Locked.toString(), Validators.required],
      username: [this.user.UserId, Validators.required],
      newPassword: [''],
      confirmPassword: [''],
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

  closeMe() {
    this.activeModal.close();
  }

  onSubmit() {
    this.submitted = true;
    // console.log('Form Values', this.userForm.value);

    if (this.userForm.valid) {

      this._passwordCheck();

      if (this.isPasswordValidated) {

        this.isRequested = false;
        const userName = this.user.UserName;
        const username = this.userForm.controls['username'].value;
        this._sendUpdateRequest(this.userForm.value, userName, username);

      }

    }
  }

  _sendUpdateRequest(data, userName, username) {
    this.api.post('Users/Update', data).then((response: any) => {

      this.isRequested = true;
      this.helper.successBigToast('Success', 'Successfully updated: ' + userName + '\'s Account');

    }, (error: any) => {

      this.isRequested = true;

      if (error.error.Message) {
        if (error.error.Message === 'Already Exists') {
          // tslint:disable-next-line: max-line-length
          this.helper.failureBigToast('Failed!', '"' + username + '" is already assigned to another user, kindly user different username for login.');
          return;
        }
      }

      this.helper.failureBigToast('Failed!', 'Invalid data, kindly check updated data.');
    });
  }

  _passwordCheck() {
    const password = this.userForm.controls['newPassword'].value;
    const confirmPassword = this.userForm.controls['confirmPassword'].value;

    if (password !== '') {
      if (password !== confirmPassword) {
        this.userForm.controls['confirmPassword'].setErrors({ NoPassswordMatch: true });
        this.isPasswordValidated = false;
      }
    }

  }


}
