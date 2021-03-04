import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { SendMailService } from '../send-mail.service';
import { InboxService } from '../inbox.service';


export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}
@Component({
  selector: 'app-new-message',
  templateUrl: './new-message.component.html',
  styleUrls: ['./new-message.component.scss'],
  providers: [SendMailService]
})
export class NewMessageComponent implements OnInit {
  user: any;
  mailId
  error: {};
  message
  subject
  sendDate
  fromAddress
  ccAddress
  ccoAddress
  selectedFile = {name: null};
  dataSource
  receipiets: []
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private inboxService: InboxService,
    private service: SendMailService,
    private _snackBar: MatSnackBar,
  ) {
    let aux = localStorage.getItem('user');
    this.user = JSON.parse(aux);

  }

  ngOnInit(): void {

    this.mailId = this.route.snapshot.paramMap.get('mailId');

    if (this.mailId) {
      this.service.getMail(this.mailId).subscribe(
        response => {
          console.log(response);
          const date = new Date(response.sendDate);
          this.message = `On ${date} ${response.fromAddress} wrote: \n ${response.message}`;
          this.messageFormControl.setValue(this.message);
          this.subject = `Re: ${response.subject}`;
          this.subjectFormControl.setValue(this.subject);
          this.sendDate = response.sendDate;
          this.fromAddress = response.toAddress;
          this.toFormControl.setValue(this.fromAddress);
          this.ccAddress = response.toCcAddress;
          this.ccFormControl.setValue(this.ccAddress);
          this.ccoAddress = response.toCcoAddress;
          this.ccoFormControl.setValue(this.ccoAddress);
        },
        error => {
          console.log(error);
          return this.error = error;
        }

      );
    }
  }

  back() {
    this.router.navigate(['/mail']);
  }
  
  splitReceipientsArray (string) {
    return string != '' ? string.split(/\s*,\s*/) : [];
  }

  getReceipients() {
    const to = this.splitReceipientsArray(this.toFormControl.value + '');
    const cc = this.splitReceipientsArray(this.ccFormControl.value + '');
    const cco = this.splitReceipientsArray(this.ccoFormControl.value + '');
    if (cc.length != 0) {
      cc.map((value)=> {
        to.push(value);
      });
    }
    if (cco.length != 0) {
      cco.map((value)=> {
        to.push(value);
      });
    }
    this.receipiets = to;
  }

  openSnackBar() {
    this._snackBar.open(`Mensaje Enviado`, 'X', {
      duration: 2000,
    });
  }

  send() {
    this.getReceipients();
    this.service.sendMail(
      this.user.mail,
      this.receipiets,
      this.subjectFormControl.value,
      this.messageFormControl.value
    ).subscribe(
      response => {
        if (this.selectedFile.name != null) {
          this.inboxService.onUpload(this.selectedFile, response.id ).subscribe(
            response => {
              this.back();
              return this.user = response;
            },
            error => {
              console.log(error);
              return this.error = error;
            }
          );
        }
        this.openSnackBar();
        this.back();
      },
      error => {
        return this.error = error;
      }
    );
  }

  public onFileChange(event) {
    //Select File
    this.selectedFile = event.target.files[0];  
    console.log(this.selectedFile);
  }

  toFormControl = new FormControl('', [
    Validators.required,
  ]);

  subjectFormControl = new FormControl('');
  messageFormControl = new FormControl('');
  ccFormControl = new FormControl('');
  ccoFormControl = new FormControl('');

  matcher = new MyErrorStateMatcher();
}
