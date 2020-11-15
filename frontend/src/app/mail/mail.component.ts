import { Component, OnInit, ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import { InboxService } from '../inbox.service';
import { SentService } from '../sent.service';
import { Router } from '@angular/router';

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
  displayedColumns: any;
  dataSource: any;
  constructor(
      private inboxService: InboxService,
      private sentService: SentService,
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

  showIncome () {
    this.title = "Recibidos";
    this.inboxService.getInboxMessages(this.user.mail).subscribe(
      response => {
      console.log(response);
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
    this.title = "Enviados";
    this.sentService.getSentMessages(this.user.mail).subscribe(
      response => {
      console.log(response);
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
    this.router.navigate([`/mailView/:${id}`]);
  }
  
  sendNewMail() {
    this.router.navigate(['/newMail']);
  }

  ngAfterViewInit() {
  //  this.dataSource.paginator = this.paginator;
  }
}
