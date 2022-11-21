import {Component, OnInit, OnDestroy, HostListener} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { Offer } from '../offer.model';
import { OfferService } from '../offer.service';
import {Subscription} from "rxjs";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-recipe-list',
  templateUrl: './offer-list.component.html',
  styleUrls: ['./offer-list.component.css']
})
export class OfferListComponent implements OnInit, OnDestroy {
  recipes!: Offer[];
  subscription!: Subscription;
  currentPageIndex: number = 0
  numberOfCol: number = 3
  public innerWidth: any;

  constructor(private recipeService: OfferService,
              private router: Router,
              private route: ActivatedRoute) {
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    console.log(window.innerWidth)
    this.innerWidth = window.innerWidth;
    this.numberOfCol= Math.floor(this.innerWidth/500)
    if(this.numberOfCol<1)
      this.numberOfCol = 1
  }

  ngOnInit() {
    this.innerWidth = window.innerWidth;
    this.subscription = this.recipeService.recipesChanged
      .subscribe(
        (recipes: Offer[]) => {
          this.recipes = recipes;
        }
      );
    this.recipes = this.recipeService.getRecipes();
  }

  onNewRecipe() {
    this.router.navigate(['new'], {relativeTo: this.route});
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  currentPageChng(event: PageEvent){
    console.log(this.currentPageIndex)
    this.currentPageIndex = event.pageIndex
  }
}
