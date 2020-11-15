import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MailComponent } from './mail/mail.component';
import { MessageComponent } from './message/message.component';
import { NewMessageComponent } from './new-message/new-message.component';

const routes: Routes =  [
{ path: 'login', component: LoginComponent },
{ path: 'mail', component: MailComponent },
{ path: 'mailView/:userId', component: MessageComponent },
{ path: 'newMail/:mailId', component: NewMessageComponent }
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
