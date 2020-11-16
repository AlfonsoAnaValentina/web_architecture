import { Component, OnInit, ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import { InboxService } from '../inbox.service';
import { SentService } from '../sent.service';
import { SendMailService } from '../send-mail.service';
import { Router } from '@angular/router';
import { Subscriber } from 'rxjs';

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  styleUrls: ['./mail.component.scss']
})
export class MailComponent implements OnInit {
  isOpen: boolean;
  title: string;
  inbox: [];
  error: {};
  user: any;
  length = 0;
  page = 0;
  pageSize = 10;
  displayedColumns: any;
  dataSource: any;
  constructor(
      private inboxService: InboxService,
      private sentService: SentService,
      private sendMailService: SendMailService,
      private router: Router
    ) { 
    this.isOpen = false;
    this.title = "Recibidos";
    let aux = localStorage.getItem('user');
    this.user = JSON.parse(aux);
    this.showIncome();
    this.displayedColumns = ['id', 'fromAddress', 'subject', 'sendDate'];
    //dataSource = this.title === 'Recibidos' ? this.inbox;
    this.dataSource = this.inbox;
    
    //@ViewChild(MatPaginator) paginator: MatPaginator;

  }

  ngOnInit(): void {
    
  }

  open(){
    this.isOpen = !this.isOpen;
  }


  onPaginateChange(e){
    this.page = e.pageIndex;
    
    if (this.title == "Recibidos")
      this.showIncome();
    else
      this.showSent();

  }
  

  showIncome () {

    if (this.title != "Recibidos")
      this.page = 0;

    this.title = "Recibidos";
    this.inboxService.getInboxMessages(this.user.mail, this.page).subscribe(
      response => {
      console.log(response);
      this.length = response.totalElements

      const data = this.dataSource = response.content; 
      return data;
      },
      error => {
        console.log(error);
        return this.error = error;
      }
    );
  }

  showSent () {
    if (this.title != "Enviados")
      this.page = 0;

    this.title = "Enviados";
    this.sentService.getSentMessages(this.user.mail, this.page).subscribe(
      response => {
      console.log(response);
      this.length = response.totalElements;

      const data = this.dataSource = response.content; 
      return data;
      },
      error => {
        console.log(error);
        return this.error = error;
      }
    );
  }

  readMail(id) {
    const mail = this.sendMailService.getMail(id);
    this.inboxService.setAsRead(id, mail).subscribe(
      response => {
        this.router.navigate([`/mailView/${id}`]);
        
        },
        error => {
          console.log(error);
          return this.error = error;
        }
    );
    
  }
  
  sendNewMail() {
    this.router.navigate(['/newMail/0']);
  }




  ngAfterViewInit() {
  //  this.dataSource.paginator = this.paginator;
  }
}
