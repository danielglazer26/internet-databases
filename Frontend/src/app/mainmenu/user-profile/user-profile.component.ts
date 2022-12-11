import { Component, OnInit } from '@angular/core';
import {Offer} from "../offers/offer.model";
import {DataStorageService} from "../connection/shared/data-storage.service";
import {CookieSessionStorageService} from "../connection/session/cookie-session-storage.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  offers: Offer[] = [];
  subscription!: Subscription;

  constructor(private dataStorageService: DataStorageService,
              private cookieSessionStorageService: CookieSessionStorageService) {
    this.dataStorageService.fetchUserOffers(cookieSessionStorageService.getUser().login)
  }

  ngOnInit() {
    this.subscription = this.dataStorageService.userOffersChanged
      .subscribe(
        (offers: Offer[]) => {
          this.offers = offers;
        }
      );
    this.offers = this.dataStorageService.getUserOffers()
    console.log(this.offers.length)
  }

}
