import {Component} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {RequestManagerService} from "../connection/http/request-manager.service";
import {HashPasswordService} from "../connection/authorization/hash-password.service";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  showForm = true
  accountForm: any;
  submitted = false;
  showLoginBusy: boolean = false;
  errorMessage !: string

  constructor(fb: FormBuilder, private router: Router, private requestManager: RequestManagerService,
              private bcrypt : HashPasswordService) {
    this.accountForm = new FormGroup<any>({
        login: fb.control('', Validators.required),
        password: fb.control('', [Validators.minLength(8), Validators.required]),
        confirmPassword: fb.control('', [Validators.required]),
        email: fb.control('', [Validators.email, Validators.required])
      },
      {
        validators: [PasswordValidation.matchPassword('password', 'confirmPassword')]
      }
    )
  }


  get f(): { [key: string]: AbstractControl } {
    return this.accountForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    if (this.accountForm.invalid) {
      return;
    }
    const jsonObject = JSON.parse(JSON.stringify(this.accountForm.value, null, 4))
    delete jsonObject.confirmPassword

    jsonObject.password = this.bcrypt.makeHash(jsonObject.password)

    this.requestManager.registerAccount(JSON.stringify(jsonObject, undefined, 4)).subscribe({
      error: err => {
          this.errorMessage = err.error.message
          this.showLoginBusy = true
          setTimeout(()=>{
            this.showLoginBusy = false
          }, 5000)
      },
      complete: () =>{
        this.showForm = false
        setTimeout(()=>{
          this.router.navigateByUrl('/login')
        }, 3000)
      }
    })
  }

}

export default class PasswordValidation {
  static matchPassword(password: string, confirmationPassword: string): ValidatorFn {
    return (controls: AbstractControl) => {
      const control = controls.get(password);
      const checkControl = controls.get(confirmationPassword);

      if (checkControl?.errors && !checkControl.errors.matching) {
        return null;
      }

      if (control?.value !== checkControl?.value) {
        checkControl?.setErrors({matching: true});
        return {matching: true};
      } else {
        return null;
      }
    };
  }
}

