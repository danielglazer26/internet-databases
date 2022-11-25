import {Injectable} from '@angular/core';


const USER_KEY = 'auth-user';


@Injectable({
  providedIn: 'root'
})
export class CookieSessionStorageService {

  constructor() {
  }

  clean(): void {
    window.sessionStorage.removeItem(USER_KEY)
    window.sessionStorage.clear()
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY)
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user))
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY)
    return user != null ? JSON.parse(user) : {}
  }

  public isLoggedIn(): boolean {
    const user = window.sessionStorage.getItem(USER_KEY)
    return !!user
  }
}
