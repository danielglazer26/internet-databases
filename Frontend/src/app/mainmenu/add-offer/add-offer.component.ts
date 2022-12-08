import {Component} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup} from "@angular/forms";
import {AnnouncementFormGroup} from "./announcement-form";
import {RequestManagerService} from "../connection/http/request-manager.service";
import {CookieSessionStorageService} from "../connection/session/cookie-session-storage.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent {

  profileForm!: FormGroup;

  type: boolean = true
  showSuccess: boolean = true
  isAddEnable: boolean = true
  announcementSubmitted: boolean = false
  announcementId: number = -1
  gallery: Array<number> = new Array<number>()


  constructor(fb: FormBuilder, private requestManager: RequestManagerService,
              private cookieStorage: CookieSessionStorageService, private router: Router) {
    this.profileForm = new AnnouncementFormGroup(fb)
  }

  ngOnInit(): void {
    const raw = JSON.stringify({
      "title": "announcementInProduction;",
      "additionalDescription": "test1",
      "announcementType": 0,
      "ownerLogin": this.cookieStorage.getUser().login,
      "apartmentNumber": -1,
      "street": "test2",
      "city": "test3",
      "province": 0,
      "costPerMonth": -2,
      "rent": -3,
      "deposit": -4,
      "roomNumber": -5,
      "area": -6,
    });
    this.requestManager.createAnnouncement(raw).subscribe({
      next: value => {
        this.announcementId = parseInt(value.message.split(": ")[1])
        console.log(this.announcementId)
      },
      error: err => console.log(err)
    })
  }

  get f(): { [key: string]: AbstractControl } {
    return this.profileForm.controls;
  }

  ngOnDestroy(): void {
    if (!this.announcementSubmitted) {
      this.requestManager.destroyAnnouncement(this.announcementId).subscribe({
        next: value => console.log(value),
        error: err => console.log(err)
      })
    }
  }


  onTypeChange(type: number) {
    this.type = (type == 0)
    let jsonObject = JSON.parse(JSON.stringify(this.profileForm?.value, undefined, 4));
    jsonObject.announcementType = this.type ? 0 : 1
    // @ts-ignore
    this.profileForm.patchValue(jsonObject)
  }

  fileInputChange(fileInputEvent: any) {
    let formData = new FormData();
    formData.append("image", fileInputEvent.target.files[0]);
    formData.append("announcementId", "1");
    this.requestManager.uploadPhoto(formData).subscribe({
      next: value => {
        let photoId = parseInt(value.message)
        this.gallery.push(photoId)
        console.log(photoId)
      },
      error: err => console.log(err)
    })
    // console.log(fileInputEvent.target.files[0]);
  }

  getIsAddEnable() {
    return this.isAddEnable
  }

  onSubmit() {
    this.announcementSubmitted = true
    console.log(this.gallery.length)
    if (this.gallery.length === 0) {
      return;
    }
    if (this.profileForm.invalid) {
      return;
    }


    const jsonObject = JSON.parse(JSON.stringify(this.profileForm.value, null, 4))
    jsonObject.announcementId = this.announcementId
    jsonObject.ownerLogin = this.cookieStorage.getUser().login
    this.requestManager.updateAnnouncement(jsonObject).subscribe({
      error: err => console.log(err),
      complete: () => {
        this.showSuccess = false
        setTimeout(() => {
          this.router.navigateByUrl('/announcements/')
        }, 1500)
      }
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
