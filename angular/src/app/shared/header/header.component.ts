import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
declare var $: any;

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor(public auth: AuthService) { }

  ngOnInit() {
    $.getScript('assets/js/script.js');
  }

}
