import {Component} from '@angular/core';
import {CookieSessionStorageService} from "../connection/session/cookie-session-storage.service";
import {RequestManagerService} from "../connection/http/request-manager.service";

@Component({
  selector: 'app-sidenav-wrapper',
  templateUrl: './sidenav-wrapper.component.html',
  styleUrls: ['./sidenav-wrapper.component.scss']
})
export class SidenavWrapperComponent {

  isExpanded: boolean = false;

  currentMenu: number = 0

  constructor(private cookieStorage: CookieSessionStorageService, private requestManager: RequestManagerService) {
  }

  ngOnInit(): void {
  }

  onMenuChange(menuId: number) {
    this.currentMenu = menuId
  }

  menuItemStyleClassResolver(menuId: number) {
    if (menuId == this.currentMenu)
      return "background: #00b6bd;"
    else
      return ""
  }

  logout() {
    this.requestManager.logoutAccount().subscribe({
      next: () => this.cookieStorage.clean()
    });

    window.location.reload();
  }
}
