import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestApiService } from 'src/app/services/api/rest-api.service';
import { HelperService } from 'src/app/services/helper/helper.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-suspicious-transaction',
  templateUrl: './suspicious-transaction.component.html',
  styleUrls: ['./suspicious-transaction.component.scss']
})
export class SuspiciousTransactionComponent implements OnInit {

  @Input() siName;
  @Input() siAddress;
  @Input() siCellNo;
  @Input() siCNIC;
  @Input() stiAmount;

  submitted = false;
  isDataLoaded = true;
  isRequested = true;

  isValidDataAdded = false;

  sptForm: FormGroup;

  userId;


  constructor(private fb: FormBuilder, private api: RestApiService, private helper: HelperService,
    private auth: AuthService, private router: Router, private activeModal: NgbActiveModal) {
    if (this.auth.user.UserID) {
      this.userId = this.auth.user.UserID;
    } else {
      this.router.navigateByUrl('auth/login');
    }
  }

  get f() { return this.sptForm.controls; }

  ngOnInit() {

    this.submitted = false;

    this.sptForm = this.fb.group({
      date: [{ value: this.helper.getCurrentDateForm(), disabled: true }, Validators.required],
      initialReport: ['true', Validators.required],
      roName: ['', Validators.required],
      roDesignation: ['', Validators.required],
      roPhoneNo: ['', Validators.required],
      roFaxNo: ['', Validators.required],
      asstName: ['', Validators.required],
      asstDesignation: ['', Validators.required],
      asstPhoneNo: ['', Validators.required],
      asstFaxNo: ['', Validators.required],
      siName: [this.siName, Validators.required],
      siAddress: [this.siAddress, Validators.required],
      siCellNo: [this.siCellNo, Validators.required],
      siCNIC: [this.siCNIC, Validators.required],
      siOccupation: ['', Validators.required],
      siActs: ['', Validators.required],
      stiAmount: [this.stiAmount, Validators.required],
      stiNarrative: ['', Validators.required],
      aiTransactionMethod: ['', Validators.required],
      correctedReport: [''],
      supplementalReport: [''],
      nameOfInstitution: [''],
      primaryRegulator: [''],
      isRelationShipMentained: ['Yes'],
      natureOfAccount: [''],
      userId: [this.userId, Validators.required],
    });

  }


  closeMe() {
    this.activeModal.close(this.isValidDataAdded);
  }

  onSubmit() {
    this.submitted = true;

    if (this.sptForm.valid) {
      this.isRequested = false;

      this.isValidDataAdded = true;

      this.closeMe();

    } else {
      console.log('Invalid SPT Form!');
    }
  }


}
