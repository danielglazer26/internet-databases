import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

const ADDRESS = "http://localhost:8080";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class RequestManagerService {

  constructor(private http: HttpClient) {
  }

  registerAccount(json: string): Observable<any> {
    return this.http.post(ADDRESS + "/api/auth/register", json, httpOptions)
  }

  loginAccount(json: string): Observable<any> {
    return this.http.post(ADDRESS + "/api/auth/login", json, httpOptions)
  }

  logoutAccount(): Observable<any> {
    return this.http.post(ADDRESS + "/api/auth/logout", null)
  }

  createAnnouncement(json: string): Observable<any> {
    return this.http.post(ADDRESS + "/authenticated/addAnnouncement", json, httpOptions)
  }

  //todo test this to
  updateAnnouncement(json: string): Observable<any> {
    return this.http.put(ADDRESS + "/authenticated/updateAnnouncement", json, httpOptions)
  }

  //todo test it
  destroyAnnouncement(announcementId: number): Observable<any> {
    return this.http.delete(ADDRESS + "/authenticated/destroyAnnouncement" + "?announcementId=" + announcementId)
  }

  uploadPhoto(file: FormData): Observable<any> {
    return this.http.post(ADDRESS + "/authenticated/upload", file)
  }
}
