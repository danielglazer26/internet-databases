import {Component, Input} from '@angular/core';
import {Offer} from "../../offers/offer.model";
import {DataStorageService} from "../../connection/shared/data-storage.service";
import {CookieSessionStorageService} from "../../connection/session/cookie-session-storage.service";

@Component({
  selector: 'app-offer-with-delete',
  templateUrl: './offer-with-delete.component.html',
  styleUrls: ['./offer-with-delete.component.css']
})
export class OfferWithDeleteComponent  {
  @Input() offer!: Offer;
  @Input() index!: number;
  source = 1
  constructor(private dataStorageService: DataStorageService,
              private cookieSessionStorageService: CookieSessionStorageService) {
  }

  deleteOffer(){
    this.dataStorageService.destroyOffer(this.offer.announcementId, this.cookieSessionStorageService.getUser().login)
  }

  fetchPhotos(){
    this.dataStorageService.fetchPhotos(this.offer.announcementId)
  }

  getCostText(){
    return this.offer.costPerMonth + " zł"
  }

  getAddress(){
    return this.offer.city + ", " + this.offer.street
  }

  getRoomNumber(){
    let nr = this.offer.roomNumber
    if(nr == 1){
      return nr + " pokój"
    }else if(nr%10 >= 2 && nr%10 <= 4 && nr > 20){
      return nr + " pokoje"
    }else{
      return nr + " pokoi"
    }
  }

  getArea(){
    return this.offer.area + " m2"
  }
}
