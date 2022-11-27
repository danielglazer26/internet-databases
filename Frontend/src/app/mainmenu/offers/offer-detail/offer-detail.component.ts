import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';

import { Offer } from '../offer.model';
import {DataStorageService} from "../../../shared/data-storage.service";

@Component({
  selector: 'app-offer-detail',
  templateUrl: './offer-detail.component.html',
  styleUrls: ['./offer-detail.component.css']
})
export class OfferDetailComponent implements OnInit {
  offer!: Offer;
  index!: number;

  constructor(private dataStorageService: DataStorageService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.params
      .subscribe(
        (params: Params) => {
          this.index = +params['index'];
          this.offer = this.dataStorageService.getOffer(this.index);
        }
      );
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
