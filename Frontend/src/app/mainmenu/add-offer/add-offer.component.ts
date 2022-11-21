import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent implements OnInit {

  rent: boolean = true

  constructor() {
  }

  ngOnInit(): void {
  }

  onTypeChange(type: number) {
    this.rent = (type == 0)
  }

  fileInputChange(fileInputEvent: any) {
    console.log(fileInputEvent.target.files[0]);
  }

  isAddEnable(){
    return true
  }
}
