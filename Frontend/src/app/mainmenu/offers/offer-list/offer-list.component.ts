import {Component, OnDestroy, OnInit} from '@angular/core';

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

  constructor(private dataStorageService: DataStorageService) {
    this.dataStorageService.fetchOffers(this.limit, this.currentPageIndex * this.limit, this.minPrice, this.maxPrice, this.minArea, this.maxArea, this.roomNumber, this.city, this.street, this.type)
  }

  runRequest() {
    this.dataStorageService.fetchOffers(this.limit, this.currentPageIndex * this.limit, this.minPrice, this.maxPrice, this.minArea, this.maxArea, this.roomNumber, this.city, this.street, this.type)
  }

  ngOnInit() {
    this.innerWidth = window.innerWidth;
    this.subscription = this.dataStorageService.offersChanged
      .subscribe(
        (recipes: Offer[]) => {
          this.offers = recipes;
          this.currentPageIndex = 0
        }
      );
    this.offers = this.dataStorageService.getOffers()
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  currentPageChange(event: PageEvent) {
    this.currentPageIndex = event.pageIndex
  }
}
