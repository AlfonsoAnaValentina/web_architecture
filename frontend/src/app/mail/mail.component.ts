import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {MatMenuModule} from '@angular/material/menu';
import { InboxService } from '../inbox.service';
import { SentService } from '../sent.service';
import { SendMailService } from '../send-mail.service';
import { FoldersService } from '../folders.service';
import { Router } from '@angular/router';

export interface DialogData {
  name: string;
}

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
  newFolder: any;
  name: string;
  constructor(
      private inboxService: InboxService,
      private sentService: SentService,
      private sendMailService: SendMailService,
      private folderService: FoldersService,
      private router: Router,
      private _snackBar: MatSnackBar,
      private _menuModel: MatMenuModule,
      public dialog: MatDialog
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
    this.folderService.getAllFolders(this.user.id).subscribe(
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

  setLabel(idF) {
    let selected = this.dataSource.filter(f => {
      return f.checked;
    });
    if (selected.length === 0) {
      this.openSnackBar('etiquetar', null);
    } else {
      selected.forEach(element => {
        const mail = {"id": 0, "idMessage": element.id, "idFolder": idF};
        this.folderService.addMailToLabel(mail).subscribe(
          response => {
            console.log(response);
            this.openSnackBar('', 'Mensaje(s) etiquetado(s)');
            return response;
            },
            error => {
              console.log(error);
              return this.error = error;
            }
        );  
      });
    }
  }

  deleteMail() {
    let selected = this.dataSource.filter(f => {
      return f.checked;
    });
    if (selected.length === 0) {
      this.openSnackBar('eliminar', null);
    } else {
      this.inboxService.deleteMessages(selected).subscribe(
        response => {
          console.log(response);
          location.reload();
          this.openSnackBar('', 'Mensaje(s) Eliminado(s)');
          return response;
          },
          error => {
            console.log(error);
            return this.error = error;
          }
      );
    }
  }

  openSnackBar(verb, msg) {
    const msgDisplay = verb != '' ? `Seleccione uno o mas mensajes a ${verb}.` : msg;
    this._snackBar.open(msgDisplay, 'X', {
      duration: 4000,
    });
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(FolderDialog, {
      width: '250px',
      data: {name: this.name }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      this.newFolder = {
        id: 0,
        label: result,
        idUser: this.user.id
      };
      this.name = result;
      this.folderService.createNewFolder(this.newFolder).subscribe(
        response => {
            console.log('Folder created!');
            this.openSnackBar('', 'Carpeta Creada');
          },
          error => {
            console.log(error);
            return this.error = error;
          }
      );
    });
  }

}

@Component({
  selector: 'folder-dialog',
  templateUrl: 'folder-dialog.html',
})
export class FolderDialog {

  constructor(
    public dialogRef: MatDialogRef<FolderDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

}
