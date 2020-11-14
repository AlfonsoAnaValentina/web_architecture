import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MailComponent } from './mail/mail.component';

const routes: Routes =  [
{ path: 'login', component: LoginComponent },
{ path: 'mail', component: MailComponent }
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
