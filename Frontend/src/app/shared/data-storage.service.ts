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
  userOffersChanged = new Subject<Offer[]>();
  photosChanged = new Subject<number[]>();
  offers: Offer[] = [];
  userOffers: Offer[] = [];
  currentPhotos: number[] = []

  getOffers() {
    return this.offers;
  }

  getUserOffers() {
    return this.userOffers;
  }

  getOffer(id: number) {
    return this.offers[id];
  }

  getCurrentPhotos(){
    return this.currentPhotos
  }

  fetchPhotos(announcementId: number){

    let params = new HttpParams().append('announcementId', announcementId);
    this.http
      .get<number[]>(
        'http://localhost:8080/public/announcementPhotos/',
        {
          params: params
        }
      )
      .pipe(map(ids => {return ids}),
        tap(ids => {
          console.log(ids.length)
          this.currentPhotos = ids
          this.photosChanged.next(ids.slice());
        })
      ).subscribe(e => {
        console.log("OWIP2")
      })
  }

  fetchUserOffers(personId: number){
    let params = new HttpParams().append('limit', 1000).append('offset', 0)
   // params.append('personId', personId)
    this.http
      .get<Offer[]>(
        'http://localhost:8080/public/announcements/',
        {
           params: params
        }
      )
      .pipe(
        map(offers => {
          return offers.map(offer => {
            return {
              ...offer,
            };
          });
        }),
        tap(offers => {
          console.log(offers.length)
          this.userOffers = offers
          this.userOffersChanged.next(offers.slice());
        })
      ).subscribe(e => {
      console.log("OWIP222")
    })
  }

  fetchOffers(limit: number, offset: number, minCost?: number, maxCost?: number, minArea?: number, maxArea?: number, roomNumber?: number, city?: string, street?: string, type?: string) {

    let params = new HttpParams().append('limit', limit).append('offset', offset)
    let typeAn = 0
    if(type == "Sprzedaż"){
      typeAn = 1
    }

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
    if (city != null && city !== "") {
      params = params.append('city', city)
    }
    if (street != null && street !== "") {
      params = params.append('street', street)
    }

    params = params.append('announcementType', typeAn)
    console.log(typeAn)

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
      console.log("OWIP")
    })
  }
}
