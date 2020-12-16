import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { RegisterService } from '../register.service';
import { LocationService } from '../location.service';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
interface SelectData {
  value: string;
  viewValue: string;
}
@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.scss'],
  providers: [RegisterService, LocationService],
})
export class RegisterUserComponent implements OnInit {
  user: any;
  error: {};
  countries: any;
  provinces: any;
  cities: any;
  foods: SelectData[] = [
    {value: 'steak-0', viewValue: 'Steak'},
    {value: 'pizza-1', viewValue: 'Pizza'},
    {value: 'tacos-2', viewValue: 'Tacos'}
  ];

  constructor(
    private service: RegisterService,
    private locationService: LocationService,
    private router: Router) { 
    
    this.getCountries();
      
  }

  ngOnInit(): void {
  }

  registerUser() {
    console.log('registrar usuario');
    const newUsr = {
      "address": this.addressFormControl.value,
      "alternativeMail": this.alternativeEmailFormControl.value,
      "city": this.cityFormControl.value,
      "country": this.countryFormControl.value,
      "id": 0,
      "lastName": this.lastNameFormControl.value,
      "mail": this.emailFormControl.value,
      "name": this.nameFormControl.value,
      "password": this.passwordFormControl.value,
      "phone": this.phoneFormControl.value,
      "province": this.provinceFormControl.value
    }
    console.log(this.countryFormControl.value);
     this.service.setUser(newUsr).subscribe(
      response => {
      this.router.navigate(['/mail']);
      return this.user = response;
      },
      error => {
        return this.error = error;
      }
    );
  }

  getCountries() {
    this.locationService.getCountries().subscribe(
      response => {
      console.log(response);

      const data = this.countries = this.parseData(response.content); 
      console.log(data);
      return data;
      },
      error => {
        console.log(error);
        return this.error = error;
      }
    );
    
  }

  getProvinces() {

    this.locationService.getProvincesByCountry(this.countryFormControl.value).subscribe(
      response => {
      console.log(response);

      const data = this.provinces = this.parseData(response.content); 
      console.log(data);
      return data;
      },
      error => {
        console.log(error);
        return this.error = error;
      }
    );
  }

  getCities() {
    this.locationService.getCitiesByProvince(this.provinceFormControl.value).subscribe(
      response => {
      console.log(response);

      const data = this.cities = this.parseData(response.content); 
      console.log(data);
      return data;
      },
      error => {
        console.log(error);
        return this.error = error;
      }
    );
  }

  parseData(parseData) {
    const data = [];
    parseData.map((value) => {
      data.push({ value: value.id, viewValue: value.name });
    });
    return data;
  }

  isInvalid() {
    return this.passwordFormControl.invalid || this.emailFormControl.invalid 
    || this.countryFormControl.invalid || this.provinceFormControl.invalid || this.cityFormControl.invalid 
    || this.nameFormControl.invalid || this.lastNameFormControl.invalid || this.addressFormControl.invalid || this.phoneFormControl.invalid
    || this.alternativeEmailFormControl.invalid;
  }

  countryFormControl = new FormControl('', [
    Validators.required,
  ]);

  provinceFormControl = new FormControl('', [
    Validators.required,
  ]);

  cityFormControl = new FormControl('', [
    Validators.required,
  ]);

  nameFormControl = new FormControl('', [
    Validators.required,
  ]);

  lastNameFormControl = new FormControl('', [
    Validators.required,
  ]);

  addressFormControl = new FormControl('', [
    Validators.required,
  ]);

  phoneFormControl = new FormControl('', [
    Validators.required,
  ]);

  alternativeEmailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  emailFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  passwordFormControl = new FormControl('', [
    Validators.required,
  ]);

  matcher = new MyErrorStateMatcher();
}
