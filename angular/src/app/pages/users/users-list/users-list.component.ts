import { Component, OnInit } from '@angular/core';
import { RestApiService } from '../../../services/api/rest-api.service';
import { Router } from '@angular/router';
import { HelperService } from '../../../services/helper/helper.service';
import { UsersAddComponent } from '../users-add/users-add.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UsersEditComponent } from '../users-edit/users-edit.component';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnInit {

  isDataLoaded = false;
  userId;
  users = [];

  

  dtOptions: DataTables.Settings = {};


  constructor(private api: RestApiService, private router: Router, private helper: HelperService, private modalService: NgbModal) { }

  ngOnInit(): void {
    console.log("ngOnInit called")
    this.getUsers();
  }


  getUsers() {
    this.api.get('serviceProvider/get_serviceProvider').then((response: any) => {
      this.users = response;


      this.dtOptions = {
        data: this.users,
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
        rowCallback: (row: Node, data: any[] | Object, index: number) => {
          const self = this;
          // tslint:disable-next-line: deprecation
          $('td', row).unbind('click');
          // tslint:disable-next-line: deprecation
          $('td', row).bind('click', () => {
            self.selectedUser(data);
          });
          return row;
        }
      };

      this.isDataLoaded = true;
    }).catch(err => console.log('Error', err));
  }

  openAddModal() {
    const modalRef = this.modalService.open(UsersAddComponent);

    modalRef.result.then(() => { this.ngOnInit(); }, () => { this.ngOnInit(); });
  }

  selectedUser(user) {
    this._openEditModal(user);
  }

  _openEditModal(user) {
    const modalRef = this.modalService.open(UsersEditComponent);
    modalRef.componentInstance.user = user;

    // modalRef.result.then(() => { window.location.reload() }, () => { window.location.reload()});
    // modalRef.result.then(() => { this.ngOnInit(); }, () => { this.ngOnInit(); });
  }
}
