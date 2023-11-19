import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {map, tap} from 'rxjs/operators';
import {Offer} from "../../offers/offer.model";
import {Subject} from "rxjs";
import {User} from "../../administrator-panel/user.model";

const httpAddress = 'http://localhost:8080';

@Injectable({
  providedIn: 'root',
})
export class DataStorageService {
  constructor(private http: HttpClient) {
  }

  offersChanged = new Subject<Offer[]>();
  userOffersChanged = new Subject<Offer[]>();
  userInfoChanged = new Subject<User[]>();
  photosChanged = new Subject<number[]>();
  offers: Offer[] = [];
  userOffers: Offer[] = [];
  users: User[] = []
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

  getUserOffer(id: number) {
    return this.userOffers[id];
  }

  getCurrentPhotos() {
    return this.currentPhotos
  }

  getUsers() {
    return this.users;
  }

  fetchPhotos(announcementId: number) {

    let params = new HttpParams().append('announcementId', announcementId);
    this.http
      .get<number[]>(
        httpAddress + '/public/announcementPhotos',
        {
          params: params
        }
      )
      .pipe(map(ids => {
          return ids
        }),
        tap(ids => {
          this.currentPhotos = ids
          this.photosChanged.next(ids.slice());
        })
      ).subscribe(() => {
    })
  }

  destroyOffer(announcementId: number, ownerLogin: string) {
    let params = new HttpParams().append('announcementId', announcementId)
    this.http
      .delete<Offer[]>(
        httpAddress + '/authenticated/destroyAnnouncement',
        {
          params: params
        }
      ).subscribe(() => {
      this.fetchUserOffers(ownerLogin)
    })
  }

  destroyUser(userId: number) {
    let params = new HttpParams().append('personToDelete', userId)
    this.http
      .delete<Offer[]>(
        httpAddress + '/authenticated/removeUser',
        {
          params: params
        }
      ).subscribe(() => {
      this.fetchUsers()
    })
  }

  fetchUserOffers(ownerLogin: string) {
    let params = new HttpParams().append('ownerLogin', ownerLogin)
    this.http
      .get<Offer[]>(
        httpAddress + '/authenticated/getPersonalAnnouncement',
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
          this.userOffers = offers
          this.userOffersChanged.next(offers.slice());
        })
      ).subscribe(() => {
    })
  }

  fetchOffers(limit: number, offset: number, minCost?: number, maxCost?: number, minArea?: number, maxArea?: number, roomNumber?: number, city?: string, street?: string, type?: string) {

    let params = new HttpParams().append('limit', limit).append('offset', offset)
    let typeAn = 0
    if (type == "Sprzedaż") {
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
          this.offers = recipes
          this.offersChanged.next(recipes.slice());
        })
      ).subscribe(() => {
    })
  }

  fetchUsers() {
    this.http.get<User[]>(
      httpAddress + '/authenticated/listUsers'
    )
      .pipe(
        map(users => {
            return users.map(user => {
              return {...user,};
            })
          }
        ),
        tap(users => {
          this.users = users
          this.userInfoChanged.next(users.slice())
        })
      ).subscribe()
  }

}
