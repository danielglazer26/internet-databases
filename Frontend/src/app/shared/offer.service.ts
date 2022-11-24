import {Injectable} from '@angular/core';

import {Offer} from '../mainmenu/offers/offer.model';
import {Subject} from "rxjs";
import {DataStorageService} from "./data-storage.service";

@Injectable({
  providedIn: 'root',
})
export class OfferService {
  offersChanged = new Subject<Offer[]>();
  private n = Math.random()

  private recipes: Offer[] = [];

  constructor() {
  }

  getRecipes() {
    return this.recipes;
  }

  getRecipe(index: number) {
    return this.recipes[index];
  }

  setOffers(newOffers: Offer[]){
    console.log(this.n)
    console.log("set" + newOffers.length)
    this.recipes = newOffers
    this.offersChanged.next(this.recipes.slice());
    console.log(this.recipes)
  }

  addRecipe(recipe: Offer) {
    this.recipes.push(recipe);
    this.offersChanged.next(this.recipes.slice());
  }

  updateRecipe(index: number, newRecipe: Offer) {
    this.recipes[index] = newRecipe;
    this.offersChanged.next(this.recipes.slice());
  }

  deleteRecipe(index: number) {
    this.recipes.splice(index, 1);
    this.offersChanged.next(this.recipes.slice());
  }
}
