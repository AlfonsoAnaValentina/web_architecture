import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterUserComponent } from './register-user/register-user.component';
import { MailComponent } from './mail/mail.component';
import { MessageComponent } from './message/message.component';
import { NewMessageComponent } from './new-message/new-message.component';
import { 
  AuthGuardService as AuthGuard 
} from './auth-guard.service';

const routes: Routes =  [
{ path: '', component: LoginComponent },
{ path: 'login', component: LoginComponent },
{ path: 'register', component: RegisterUserComponent },
{ path: 'mail', component: MailComponent, canActivate: [AuthGuard]  },
{ path: 'mailView/:userId', component: MessageComponent, canActivate: [AuthGuard]  },
{ path: 'newMail/:mailId', component: NewMessageComponent, canActivate: [AuthGuard]  }
]

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
