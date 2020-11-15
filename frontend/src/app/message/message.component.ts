import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SendMailService } from '../send-mail.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss'],
  providers: [SendMailService]
})
export class MessageComponent implements OnInit {
  mailId: any;
  error
  mail
  subject
  message
  sendDate
  fromAddress
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sendMailService: SendMailService
  ) { }

  ngOnInit(): void {
    this.mailId = this.route.snapshot.paramMap.get('userId');
    console.log(this.mailId);
    this.getMail();
  }

  getMail() {
    this.sendMailService.getMail(this.mailId).subscribe(
      response => {
      console.log(response);
      this.mail = response; 
      this.subject = response.subject;
      this.message = response.message;
      this.sendDate = response.sendDate;
      this.fromAddress = response.fromAddress;
      },
      error => {
        console.log(error);
        return this.error = error;
      }
    );
  }

  back(){
    this.router.navigate(['/mail']);
  }
  sendReply(){
    this.router.navigate(['/newMail/' + this.mailId]);
  }

}
