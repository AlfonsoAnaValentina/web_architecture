import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
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
  mailId
  error: {};
  message
  subject
  sendDate
  fromAddress
  constructor(
    private router: Router,
    private route: ActivatedRoute,

    private service: SendMailService
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
          this.fromAddress = response.fromAddress;
          this.toFormControl.setValue(this.fromAddress);
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
