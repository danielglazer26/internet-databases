import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent implements OnInit {

  rent: boolean = true
  isAddEnable: boolean = true

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

  getIsAddEnable(){
    return this.isAddEnable
  }

  test() {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const raw = JSON.stringify({
      "title": "tyhtul;",
      "additionalDescription": "3213",
      "ownerLogin": "Daniel",
      "apartmentNumber": 74,
      "street": "Nowowiejska",
      "city": "Wroclaw",
      "costPerMonth": 1,
      "rent": 2,
      "deposit": 3,
      "roomNumber": 34,
      "area": 15,
      "photos": null
    });

    const requestOptions = {
      method: 'POST',
      headers: myHeaders,
      body: raw,
    };

    fetch("http://localhost:8080/authenticated/addAnnouncement", requestOptions)
      .then(response => response.text())
      .then(result => console.log(result))
      .catch(error => console.log('error', error));
  }
}
