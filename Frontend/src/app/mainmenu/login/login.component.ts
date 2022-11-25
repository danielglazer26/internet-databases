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
  private role!: string;

  constructor(private fb: FormBuilder, private router: Router, private requestManager: RequestManagerService, private cookieStorage: CookieSessionStorageService) {
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

    const raw = JSON.stringify(this.accountForm.value, null, 4)

    this.requestManager.loginAccount(raw).subscribe({
      next: value => {
        this.cookieStorage.saveUser(value)
        this.isLogged = true
        this.role = this.role = this.cookieStorage.getUser().role
        this.router.navigateByUrl('/')

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
