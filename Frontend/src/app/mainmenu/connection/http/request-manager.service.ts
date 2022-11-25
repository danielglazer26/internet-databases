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

  logoutAccount() : Observable<any>{
    return this.http.post(ADDRESS + "/api/auth/logout", {}, httpOptions)
  }

  createAnnouncement(json: string): Observable<any> {
    return this.http.post(ADDRESS + "/authenticated/addAnnouncement", json, httpOptions)
  }

  uploadPhoto(file: FormData): Observable<any>{
    return this.http.post(ADDRESS+"/authenticated/upload/",file)
  }

}
