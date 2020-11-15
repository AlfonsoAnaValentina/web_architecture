import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MailComponent } from './mail/mail.component';
import { MessageComponent } from './message/message.component';

const routes: Routes =  [
{ path: 'login', component: LoginComponent },
{ path: 'mail', component: MailComponent },
{ path: 'mailView/:userId', component: MessageComponent }
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
