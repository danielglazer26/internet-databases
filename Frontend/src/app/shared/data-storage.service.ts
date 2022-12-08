import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {map, tap} from 'rxjs/operators';
import {Offer} from "../mainmenu/offers/offer.model";
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root',
})
export class DataStorageService {
  constructor(private http: HttpClient) {
  }

  offersChanged = new Subject<Offer[]>();
  offers: Offer[] = [];

  getOffers() {
    return this.offers;
  }

  getOffer(id: number) {
    return this.offers[id];
  }

  fetchOffers(limit: number, offset: number, minCost?: number, maxCost?: number, minArea?: number, maxArea?: number, roomNumber?: number, city?: string, street?: string) {

    let params = new HttpParams().append('limit', limit).append('offset', offset)

    if (minCost != null) {
      params = params.append('minCost', minCost)
    }
    if (maxCost != null) {
      params = params.append('maxCost', maxCost)
    }
    if (minArea != null) {
      params = params.append('minArea', minArea)
    }
    if (maxArea != null) {
      params = params.append('maxArea', maxArea)
    }
    if (roomNumber != null) {
      params = params.append('roomNumber', roomNumber)
    }
    if (city != null) {
      params = params.append('city', city)
    }
    if (street != null) {
      params = params.append('street', street)
    }

    this.http
      .get<Offer[]>(
        'http://localhost:8080/public/announcements/',
        {
          params: params
        }
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
  }
}
