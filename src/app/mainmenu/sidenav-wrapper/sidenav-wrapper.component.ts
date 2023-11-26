import {AfterContentChecked, Component, OnInit, ViewChild} from '@angular/core';
import {CookieSessionStorageService} from "../connection/session/cookie-session-storage.service";
import {RequestManagerService} from "../connection/http/request-manager.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sidenav-wrapper',
  templateUrl: './sidenav-wrapper.component.html',
  styleUrls: ['./sidenav-wrapper.component.scss']
})
export class SidenavWrapperComponent implements OnInit, AfterContentChecked {

  block: boolean = false

  account!: string

  isExpanded: boolean = false;

  currentMenu: number = 0

  constructor(private router: Router, private cookieStorage: CookieSessionStorageService, private requestManager: RequestManagerService) {}

  ngOnInit(): void {
    this.changeAccountName();
  }

  private changeAccountName() {
    this.account = this.cookieStorage.isLoggedIn() ? this.cookieStorage.getUser().login : "Login"
  }

  onMenuChange(menuId: number) {
    this.currentMenu = menuId

    if (menuId == 4) {
      this.logout()
      this.router.navigateByUrl('')
    }
    this.changeAccountName()
  }

  menuItemStyleClassResolver(menuId: number) {
    if (menuId == this.currentMenu)
      return "background: #00b6bd;"
    else
      return ""
  }

  logout() {
    this.block = true
    this.requestManager.logoutAccount().subscribe({
      next: () => {
        this.cookieStorage.clean()
        window.location.reload()
        this.block = false
      },
      error: () => {
        this.block = false
      }
    });
  }

  isUserLogged(){
    return this.cookieStorage.isLoggedIn()
  }

  isAdministratorLogged(){
    let role = this.cookieStorage.getUser().role;
    return role != undefined && role as string == "ADMIN"
  }

  ngAfterContentChecked(): void {
    this.changeAccountName();
  }
}
