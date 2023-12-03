import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {RequestManagerService} from "../connection/http/request-manager.service";
import {CookieSessionStorageService} from "../connection/session/cookie-session-storage.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  accountForm!: FormGroup
  errorMessage: boolean = false;
  private isLogged!: boolean;

  constructor(private fb: FormBuilder, private router: Router,
              private requestManager: RequestManagerService,
              private cookieStorage: CookieSessionStorageService) {
    this.accountForm = new FormGroup<any>({
      login: fb.control('', Validators.required),
      password: fb.control('', Validators.required)
    })
  }

  ngOnInit(): void {
    if (this.cookieStorage.isLoggedIn()) {
      this.isLogged = true
    }
  }

  onSubmit() {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const jsonObject = JSON.parse(JSON.stringify(this.accountForm.value, null, 4))

    this.requestManager.loginAccount(JSON.stringify(jsonObject, undefined, 4)).subscribe({
      next: value => {
        this.cookieStorage.saveUser(value)
        this.isLogged = true
        this.router.navigateByUrl('')

      },
      error: () => {
        this.errorMessage = true
        setTimeout(() => {
          this.errorMessage = false
        }, 5000)
      },
    })

  }

  register() {
    this.router.navigateByUrl('/register')
  }

}
