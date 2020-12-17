import { Component, OnInit, ViewChild } from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import { InboxService } from '../inbox.service';
import { SentService } from '../sent.service';
import { SendMailService } from '../send-mail.service';
import { FoldersService } from '../folders.service';
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
  length = 0;
  page = 0;
  pageSize = 10;
  checked = false;
  displayedColumns: any;
  dataSource: any;
  folders: any;
  constructor(
      private inboxService: InboxService,
      private sentService: SentService,
      private sendMailService: SendMailService,
      private folderService: FoldersService,
      private router: Router,
      private _snackBar: MatSnackBar
    ) { 
    this.isOpen = false;
    this.title = "Recibidos";
    let aux = localStorage.getItem('user');
    this.user = JSON.parse(aux);
    this.showIncome();
    this.displayedColumns = ['id', 'fromAddress', 'subject', 'sendDate'];
    this.dataSource = this.inbox;
    this.showFolders();
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

  filterMails(id) {
    console.log(id);
    console.log(this.folders);
    const folder = this.folders.find(label => label.id === id );
    this.title = folder.label;
    this.inboxService.filterMessages(id, this.user.mail, this.page).subscribe(
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

  showFolders() {
    this.folderService.getAllFolders(this.user.mail).subscribe(
      response => {

      this.length = response.totalElements;

      this.folders = response;
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

  logout() {
    sessionStorage.clear();
    localStorage.clear();
    this.router.navigate(['/']);
  }

  deleteMail() {
    let selected = this.dataSource.filter(f => {
      return f.checked;
    });
    if (selected.length === 0) {
      this.openSnackBar();
    } else {
      this.inboxService.deleteMessages(selected).subscribe(
        response => {
          console.log(response);
          location.reload();
          return response;
          },
          error => {
            console.log(error);
            return this.error = error;
          }
      );
    }
  }

  openSnackBar() {
    this._snackBar.open('Seleccione uno o mas mensajes a eliminar.', 'Close', {
      duration: 2000,
    });
  }
}
