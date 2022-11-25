import {Component} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AnnouncementFormGroup} from "./announcement-form";
import {RequestManagerService} from "../connection/http/request-manager.service";

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent {

  profileForm!: FormGroup;

  type: boolean = true
  isAddEnable: boolean = true

  constructor(fb: FormBuilder, private requestManager: RequestManagerService) {
    this.profileForm = new AnnouncementFormGroup(fb)
  }


  onTypeChange(type: number) {
    this.type = (type == 0)
    let jsonObject = JSON.parse(JSON.stringify(this.profileForm?.value, undefined, 4));
    jsonObject.announcementType = this.type
    // @ts-ignore
    this.profileForm.patchValue(JSON.stringify(jsonObject))
  }

  fileInputChange(fileInputEvent: any) {
    console.log(fileInputEvent.target.files[0]);
  }

  getIsAddEnable() {
    return this.isAddEnable
  }

  onSubmit() {

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

    this.requestManager.createAnnouncement(raw).subscribe({
      next: value => console.log(value),
      error: err => console.log(err)
    })
    /* let json = JSON.stringify(this.profileForm?.value, undefined, 4);
     console.log(json)*/

    /* const myHeaders = new Headers();
     myHeaders.append("Content-Type", "application/json");


     const requestOptions = {
       method: 'POST',
       headers: myHeaders,
       body: raw,
     };

     fetch("http://localhost:8080/authenticated/addAnnouncement", requestOptions)
       .then(response => response.text())
       .then(result => console.log(result))
       .catch(error => console.log('error', error));
   }*/
  }
}
