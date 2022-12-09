import { Component, OnInit, Input } from '@angular/core';

import { Offer } from '../../offer.model';
import {HttpClient, HttpParams} from "@angular/common/http";
import {map, tap} from "rxjs/operators";
import {DataStorageService} from "../../../../shared/data-storage.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-recipe-item',
  templateUrl: './offer-item.component.html',
  styleUrls: ['./offer-item.component.css']
})
export class OfferItemComponent implements OnInit {
  @Input() offer!: Offer;
  @Input() index!: number;
  source = 0
  coverId = 1

  constructor(private dataStorageService: DataStorageService) {
  }

  ngOnInit() {
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

