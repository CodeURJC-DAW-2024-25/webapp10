import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ConcertsComponent } from './components/concerts/concerts.component';
import { EditUserComponent } from './components/user/edituser.component';
import { ErrorComponent } from './components/error/error.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';

const appRoutes: Routes = [
  { path: '', component: ConcertsComponent },
  { path: 'edit-user/:id', component: EditUserComponent},
  { path: 'error/:type', component: ErrorComponent },
  { path: '**', redirectTo: 'error/404' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}