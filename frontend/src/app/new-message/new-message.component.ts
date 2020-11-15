import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { SendMailService } from '../send-mail.service';

@Component({
  selector: 'app-new-message',
  templateUrl: './new-message.component.html',
  styleUrls: ['./new-message.component.scss'],
  providers: [SendMailService]
})
export class NewMessageComponent implements OnInit {
  user: any;
  error: {};
  constructor(
    private router: Router,
    private service: SendMailService
  ) {
    let aux = localStorage.getItem('user');
    this.user = JSON.parse(aux);
    
   }

  ngOnInit(): void {
  }

  back() {
    this.router.navigate(['/mail']);
  }

  send() {
    this.service.sendMail(
      this.user.mail,
      this.toFormControl.value,
      this.subjectFormControl.value,
      this.messageFormControl.value
    ).subscribe(
      response => {
        this.back();
      return this.user = response;
      },
      error => {
        return this.error = error;
      }
    );
  }
  toFormControl = new FormControl('');
  subjectFormControl = new FormControl('');
  messageFormControl = new FormControl('');
}
