import { Injectable } from '@angular/core';
import {
  Router, Resolve,
  RouterStateSnapshot,
  ActivatedRouteSnapshot
} from '@angular/router';
import { Observable, of } from 'rxjs';
import {DataStorageService} from "../../shared/data-storage.service";
import {Offer} from "./offer.model";
import {OfferService} from "../../shared/offer.service";

@Injectable({
  providedIn: 'root'
})
export class OfferResolverResolver implements Resolve<Offer[]> {
  constructor(
    private dataStorageService: DataStorageService,
    private recipesService: OfferService
  ) {}

  // @ts-ignore
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const recipes = this.recipesService.getRecipes();

    // if (recipes.length === 0) {
    //   return this.dataStorageService.fetchOffers();
    // } else {
    //   return recipes;
    // }
  }
}
