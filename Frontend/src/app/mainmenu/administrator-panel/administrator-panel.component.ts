import {Component, OnInit} from '@angular/core';
import {Offer} from "../offers/offer.model";
import {Subscription} from "rxjs";
import {DataStorageService} from "../connection/shared/data-storage.service";
import {CookieSessionStorageService} from "../connection/session/cookie-session-storage.service";
import {User} from "./user.model";

@Component({
  selector: 'app-administrator-panel',
  templateUrl: './administrator-panel.component.html',
  styleUrls: ['./administrator-panel.component.css']
})
export class AdministratorPanelComponent implements OnInit {
  users: User[] = [];
  subscription!: Subscription


  constructor(private dataStorageService: DataStorageService,
              private cookieSessionStorageService: CookieSessionStorageService) {
    this.dataStorageService.fetchUsers()
  }

  ngOnInit() {
    this.subscription = this.dataStorageService.userInfoChanged
      .subscribe(
        (users: User[]) => {
          this.users = users;
        }
      );
    this.users = this.dataStorageService.getUsers()
  }

}
