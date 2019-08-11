import { Component, OnInit, Input } from '@angular/core';
import { Title } from '@angular/platform-browser';

@Component({
  // tslint:disable-next-line: component-selector
  selector: 'transaction-form-section',
  templateUrl: './section.component.html',
  styleUrls: ['./section.component.scss']
})
export class SectionComponent implements OnInit {

  @Input() title;
  @Input() description;

  constructor() { }

  ngOnInit() {
  }

}
