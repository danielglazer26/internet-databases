import {Component} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {AnnouncementFormGroup} from "./announcement-form";

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent {

  profileForm!: FormGroup;

  type: boolean = true
  isAddEnable: boolean = true

  constructor(fb: FormBuilder) {
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
    let json = JSON.stringify(this.profileForm?.value, undefined, 4);
    console.log(json)
  }
}
