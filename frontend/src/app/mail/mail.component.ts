import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-mail',
  templateUrl: './mail.component.html',
  styleUrls: ['./mail.component.scss']
})
export class MailComponent implements OnInit {
  isOpen: boolean;
  title: string;
  constructor() { 
    this.isOpen = false;
    this.title = "Recibidos"
  }

  ngOnInit(): void {
  }

  open(){
    this.isOpen = !this.isOpen;
  }

  showIncome () {
    this.title = "Recibidos"
  }

  showSent () {
    this.title = "Enviados"
  }
}
