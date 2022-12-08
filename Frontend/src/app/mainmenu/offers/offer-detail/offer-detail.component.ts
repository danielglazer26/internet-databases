import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params, Router} from '@angular/router';

import {Offer} from '../offer.model';
import {DataStorageService} from "../../../shared/data-storage.service";
import {Subscription} from "rxjs";
import {CdkTableDataSourceInput} from "@angular/cdk/table";
import {MatTableDataSource} from "@angular/material/table";

@Component({
  selector: 'app-offer-detail',
  templateUrl: './offer-detail.component.html',
  styleUrls: ['./offer-detail.component.css']
})
export class OfferDetailComponent implements OnInit {
  offer!: Offer;
  index!: number;
  dataSource: Pair[] = []
  imagesId: number[] = [];
  subscription!: Subscription;
  selectedPhoto?: number = undefined
  selectedTabItem: number = 0
  displayedColumns: string[] = ["", ""]

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
          this.dataSource = [
            new Pair("Konto", this.offer.ownerLogin),
            new Pair("Numer mieszkania", this.offer.apartmentNumber.toString()),
            new Pair("Ulica", this.offer.street),
            new Pair("Miasto", this.offer.city),
            new Pair("Województwo", this.offer.province.toString()),
            new Pair("Kaucja", this.offer.rent.toString()),
            new Pair("Liczba pokoi", this.offer.costPerMonth.toString()),
            new Pair("Powierzchnia", this.offer.area.toString() + "m2")
          ]
        }
      );
    this.subscription = this.dataStorageService.photosChanged
      .subscribe(
        (ids: number[]) => {
          console.log("recieve")
          this.imagesId = ids;
        }
      );
    this.imagesId = this.dataStorageService.getCurrentPhotos();
    this.selectedPhoto = this.offer.coverPhotoId
  }

  nextPhoto(){
    if(this.selectedTabItem < this.imagesId.length-1 ){
      this.selectedTabItem++
      this.selectedPhoto = this.imagesId[this.selectedTabItem]
    }
  }

  previousPhoto(){
    if(this.selectedTabItem > 0 ){
      this.selectedTabItem--
      this.selectedPhoto = this.imagesId[this.selectedTabItem]
    }
  }

  changePhoto(id: number) {
    if (this.selectedPhoto != undefined) {

      for (let i = 0; i < this.imagesId.length; i++) {
        if( i == id){
          this.selectedTabItem = i
        }
      }
      this.selectedPhoto = id
    }
  }

  getCostText() {
    return this.offer.costPerMonth + " zł"
  }

  getAddress() {
    return this.offer.city + ", " + this.offer.street
  }

  getRoomNumber() {
    let nr = this.offer.roomNumber
    if (nr == 1) {
      return nr + " pokój"
    } else if (nr % 10 >= 2 && nr % 10 <= 4 && nr > 20) {
      return nr + " pokoje"
    } else {
      return nr + " pokoi"
    }
  }

  getArea() {
    return this.offer.area + " m2"
  }

}

class Pair{
  key: string = "";
  value: string = "";

  constructor(key: string, value: string) {
    this.key = key;
    this.value = value;
  }
}
