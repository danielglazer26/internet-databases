import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {Offer} from '../offer.model';
import {Subscription} from "rxjs";
import {PageEvent} from "@angular/material/paginator";
import {DataStorageService} from "../../connection/shared/data-storage.service";

@Component({
  selector: 'app-recipe-list',
  templateUrl: './offer-list.component.html',
  styleUrls: ['./offer-list.component.css']
})
export class OfferListComponent implements OnInit, OnDestroy {
  offers: Offer[] = [];
  subscription!: Subscription;
  currentPageIndex: number = 0
  numberOfCol: number = 3
  limit: number = 10000 // ???????
  minPrice?: number = undefined
  maxPrice?: number = undefined
  minArea?: number = undefined
  maxArea?: number = undefined
  roomNumber?: number = undefined
  type: string = "Wynajem"
  city?: string = undefined
  street?: string = undefined
  public innerWidth: any;

  constructor(private dataStorageService: DataStorageService,
              private router: Router,
              private route: ActivatedRoute) {
    this.dataStorageService.fetchOffers(this.limit, this.currentPageIndex * this.limit, this.minPrice, this.maxPrice, this.minArea, this.maxArea, this.roomNumber, this.city, this.street, this.type)
  }

  runRequest() {
    console.log(this.minPrice)
    console.log(this.maxPrice)
    console.log(this.minArea)
    console.log(this.maxArea)
    console.log(this.roomNumber)
    console.log(this.city)
    console.log(this.street)
    console.log(this.type)
    this.dataStorageService.fetchOffers(this.limit, this.currentPageIndex * this.limit, this.minPrice, this.maxPrice, this.minArea, this.maxArea, this.roomNumber, this.city, this.street, this.type)
  }

  ngOnInit() {
    this.innerWidth = window.innerWidth;
    this.subscription = this.dataStorageService.offersChanged
      .subscribe(
        (recipes: Offer[]) => {
          console.log("recieve")
          this.offers = recipes;
          this.currentPageIndex = 0
        }
      );
    this.offers = this.dataStorageService.getOffers()
    console.log(this.offers.length)
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  currentPageChng(event: PageEvent) {
    console.log(this.currentPageIndex)
    this.currentPageIndex = event.pageIndex
  }
}
