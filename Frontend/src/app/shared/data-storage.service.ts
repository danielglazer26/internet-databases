import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, tap } from 'rxjs/operators';
import {OfferService} from "./offer.service";
import {Offer} from "../mainmenu/offers/offer.model";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class DataStorageService {
  constructor(private http: HttpClient) {}
  offersChanged = new Subject<Offer[]>();
  offers: Offer[] = [];

  getOffers(){
    return this.offers;
  }

  fetchOffers(limit: number, offset: number) {
    console.log("fetch")
    this.http
      .get<Offer[]>(
        'http://localhost:8080/public/announcements/?limit=' + 10000 +'&offset=' + offset,
      )
      .pipe(
        map(recipes => {
          return recipes.map(recipe => {
            return {
              ...recipe,
            };
          });
        }),
        tap(recipes => {
          console.log(recipes.length)
          this.offers = recipes
          this.offersChanged.next(recipes.slice());
        })
      ).subscribe(e => {
        console.log("PIWO")
    })
    console.log("fetch2")
  }
}
