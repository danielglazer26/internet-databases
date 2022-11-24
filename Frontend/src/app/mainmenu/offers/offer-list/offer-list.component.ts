import {Component, OnInit, OnDestroy, HostListener, Injectable, Inject} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Offer } from '../offer.model';
import { OfferService } from '../../../shared/offer.service';
import {Subscription} from "rxjs";
import {PageEvent} from "@angular/material/paginator";
import {DataStorageService} from "../../../shared/data-storage.service";

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
  limit: number = 20
  public innerWidth: any;

  constructor(private dataStorageService: DataStorageService,
              private recipeService: OfferService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  runRequest(){
    this.dataStorageService.fetchOffers(this.limit, this.currentPageIndex*this.limit)
  }

  ngOnInit() {
    this.innerWidth = window.innerWidth;
    this.subscription = this.dataStorageService.offersChanged
      .subscribe(
        (recipes: Offer[]) => {
          console.log("recieve")
          this.currentPageIndex = 0
          this.offers = recipes;
        }
      );
    this.offers = this.dataStorageService.getOffers()
    console.log(this.offers.length)
  }

  updateOffers(){
    console.log("update")
    this.offers = this.recipeService.getRecipes()
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
