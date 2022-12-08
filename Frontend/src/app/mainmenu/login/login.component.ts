import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {RequestManagerService} from "../connection/http/request-manager.service";
import {CookieSessionStorageService} from "../connection/session/cookie-session-storage.service";
import {HashPasswordService} from "../connection/authorization/hash-password.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  accountForm!: FormGroup
  errorMessage: boolean = false;
  private isLogged!: boolean;
  private role!: string;

  constructor(private fb: FormBuilder, private router: Router,
              private requestManager: RequestManagerService,
              private cookieStorage: CookieSessionStorageService,
              private bcrypt: HashPasswordService) {
    this.accountForm = new FormGroup<any>({
      login: fb.control('', Validators.required),
      password: fb.control('', Validators.required)
    })
  }

  ngOnInit(): void {
    if (this.cookieStorage.isLoggedIn()) {
      this.isLogged = true
      this.role = this.cookieStorage.getUser().role
    }
  }

  onSubmit() {
    const myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const jsonObject = JSON.parse(JSON.stringify(this.accountForm.value, null, 4))

    jsonObject.password = this.bcrypt.makeHash(jsonObject.password)

    this.requestManager.loginAccount(JSON.stringify(jsonObject, undefined, 4)).subscribe({
      next: value => {
        this.cookieStorage.saveUser(value)
        this.isLogged = true
        this.role = this.role = this.cookieStorage.getUser().role
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
