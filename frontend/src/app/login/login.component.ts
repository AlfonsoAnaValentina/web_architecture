import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { LoginService } from '../login.service';
 
/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [LoginService],
})
export class LoginComponent implements OnInit {
  user: {};
  error: {};

  constructor(
    private service: LoginService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }


  login(){
    this.service.getUser(this.emailFormControl.value, this.passwordFormControl.value).subscribe(
      response => {
      this.router.navigate(['/mail']);
      localStorage.setItem('user', JSON.stringify(response));
      return this.user = response;
      },
      error => {
        return this.error = error;
      }
    );
  }

  isInvalid() {
    return this.passwordFormControl.invalid || this.emailFormControl.invalid;
  }

  register(){
    this.router.navigate(['/register']);
  }

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  passwordFormControl = new FormControl('', [
    Validators.required,
  ]);

  matcher = new MyErrorStateMatcher();
}
