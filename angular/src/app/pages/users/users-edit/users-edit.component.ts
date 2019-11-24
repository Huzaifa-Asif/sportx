import { Component, OnInit, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { RestApiService } from '../../../services/api/rest-api.service';
import { HelperService } from '../../../services/helper/helper.service';
import { AuthService } from '../../../services/auth/auth.service';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { UsersListComponent } from '../users-list/users-list.component';
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
  email;
  userForm: FormGroup;

  branches = [];
  services = [];

  status = [];

  isPasswordValidated = true;

  @Input() user;

  constructor(private fb: FormBuilder, private api: RestApiService, private helper: HelperService,
    private auth: AuthService, private router: Router, private activeModal: NgbActiveModal) {
    if (this.auth.user.email) {
      this.email = this.auth.user.email;
    } else {
      this.router.navigateByUrl('auth/login');
    }
  }

  ngOnInit() {

    this.submitted = false;
    this.isDataLoaded = false;

    this.status = [
      { name:"pending"},
      { name:"approved"},
      { name:"blocked"}
    ]

    this.getUsersData();


    this.userForm = this.fb.group({
      name: [this.user.name, Validators.required],
      contact: [this.user.contact, Validators.required],
      category: [this.user.category, Validators.required],
      address: [this.user.address, Validators.required],
      state: [this.user.state.toString(), Validators.required],
      email: [this.user.email, Validators.required],
      lat: [this.user.location.lat, Validators.required],
      long: [this.user.location.long, Validators.required],
      newPassword: [''],
      confirmPassword: [''],
    });

  }

  get f() { return this.userForm.controls; }

  getUsersData() {

    this.api.get('servicecategory/get_serviceCategory').then((data: any) => {
      // console.log('Data', data);
      let i=0;
      this.services=data;
      // for(i;i<data.length;i++)
      // {
      //   this.categories[i] = data[i].name;
      //   // this.services[i] = data[i].name;
      //   // console.log("service",data[i].name)
      // }

      // console.log("All services",this.services)
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
        const userName = this.user.name;
        const username = this.userForm.controls['name'].value;
        this._sendUpdateRequest(this.userForm.value, userName, username);

      }

    }
  }

  _sendUpdateRequest(data, userName, username) {
    console.log("Req",data)

    this.api.patch('serviceProvider/update_serviceProvider/', data.email, data).then((response: any) => {

      this.isRequested = true;
      this.helper.successBigToast('Success', 'Successfully updated: ' + userName + '\'s Account');
      
      setTimeout(() => 
      {
        window.location.reload();
      },
      3000);

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
