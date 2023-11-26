import {Injectable} from '@angular/core';
import {RequestManagerService} from "../http/request-manager.service";


const USER_KEY = 'auth-user';


const THIRTY_MINUTES_MS = 1800000;

@Injectable({
  providedIn: 'root'
})
export class CookieSessionStorageService {

  constructor(private requestManager: RequestManagerService) {
  }

  clean(): void {
    window.sessionStorage.removeItem(USER_KEY)
    window.sessionStorage.clear()
    this.cleanTimer()
  }

  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY)
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user))
    this.setCookieTimer()
  }

  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY)
    return user != null ? JSON.parse(user) : {}
  }

  public isLoggedIn(): boolean {
    const user = window.sessionStorage.getItem(USER_KEY)
    return !!user
  }

  private automaticLogout: NodeJS.Timeout | undefined;

  private setCookieTimer() {
    this.cleanTimer();
    this.automaticLogout = setTimeout(() => {
      this.requestManager.logoutAccount()
      this.clean()
      window.location.reload()
    }, THIRTY_MINUTES_MS);
  }

  private cleanTimer() {
    if (this.automaticLogout != undefined) {
      clearTimeout(this.automaticLogout)
    }
  }
}
