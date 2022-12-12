import {Component, Input} from '@angular/core';
import {DataStorageService} from "../../connection/shared/data-storage.service";
import {CookieSessionStorageService} from "../../connection/session/cookie-session-storage.service";
import {User} from "../user.model";

@Component({
  selector: 'app-user-to-delete',
  templateUrl: './user-to-delete.component.html',
  styleUrls: ['./user-to-delete.component.css']
})
export class UserToDeleteComponent {
  @Input() user!: User;

  constructor(private dataStorageService: DataStorageService,
              private cookieSessionStorageService: CookieSessionStorageService) {
  }

  deleteUser() {
    this.dataStorageService.destroyUser(this.user.personId)
  }
}
