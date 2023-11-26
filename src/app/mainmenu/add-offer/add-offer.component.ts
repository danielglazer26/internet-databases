import {Component} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup} from "@angular/forms";
import {AnnouncementFormGroup} from "./announcement-form";
import {RequestManagerService} from "../connection/http/request-manager.service";
import {CookieSessionStorageService} from "../connection/session/cookie-session-storage.service";
import {Router} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-add-offer',
  templateUrl: './add-offer.component.html',
  styleUrls: ['./add-offer.component.css']
})
export class AddOfferComponent {

  profileForm!: FormGroup;

  type: boolean = true
  showSuccess: boolean = true
  announcementSubmitted: boolean = false
  announcementId: number = -1
  gallery: Array<number> = new Array<number>()
  subscription!: Subscription;
  selectedPhoto?: number = undefined;

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
        console.log("On Init activity, init successful, given announcement id: " + this.announcementId)
      },
      error: err => console.log("On Init activity, init fail: " + err)
    })


  }

  changePhoto(id: number) {
    if (this.selectedPhoto != undefined) {
      this.selectedPhoto = id
    }
  }

  get f(): { [key: string]: AbstractControl } {
    return this.profileForm.controls;
  }

  ngOnDestroy(): void {
    if (!this.announcementSubmitted) {
      this.requestManager.destroyAnnouncement(this.announcementId).subscribe({
        next: value => console.log("On destroy activity, destroy successfully: " + value),
        error: err => console.log("On destroy activity, destroy fail: " + err)
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
    formData.append("announcementId", this.announcementId.toString());
    this.requestManager.uploadPhoto(formData).subscribe({
      next: value => {
        let photoId = parseInt(value.message)
        if (this.selectedPhoto == undefined) {
          this.selectedPhoto = photoId
        }
        this.gallery.push(photoId)
        console.log("Add photo activity successful, added photo id: " + photoId)
      },
      error: err => console.log("Add photo activity fail: " + err)
    })
  }

  onSubmit() {
    this.announcementSubmitted = true
    if (this.gallery.length === 0) {
      return;
    }
    if (this.profileForm.invalid) {
      return;
    }

    const jsonObject = JSON.parse(JSON.stringify(this.profileForm.value, null, 4))
    jsonObject.announcementId = this.announcementId
    jsonObject.ownerLogin = this.cookieStorage.getUser().login
    jsonObject.coverPhotoId = this.gallery[0]
    this.requestManager.updateAnnouncement(jsonObject).subscribe({
      error: err => console.log("Add announcement activity fail: " + err),
      complete: () => {
        this.showSuccess = false
        setTimeout(() => {
          this.router.navigateByUrl('/announcements/')
        }, 1500)
      }
    })

  }
}
