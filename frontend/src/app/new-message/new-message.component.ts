import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormControl, Validators } from '@angular/forms';
import { SendMailService } from '../send-mail.service';
import { InboxService } from '../inbox.service';

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
  selectedFile
  dataSource
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private inboxService: InboxService,
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
        this.inboxService.onUpload(this.selectedFile, response.id ).subscribe(
          response => {
          console.log(response);
          const data = this.dataSource = response.content;
          this.back();
          return this.user = response;
          },
          error => {
            console.log(error);
            return this.error = error;
          }
        );
      },
      error => {
        return this.error = error;
      }
    );
  }

  public onFileChange(event) {
    //Select File
    this.selectedFile = event.target.files[0];  
  }

  toFormControl = new FormControl('', [
    Validators.required,
    Validators.email,
  ]);

  subjectFormControl = new FormControl('');
  messageFormControl = new FormControl('');
}
