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
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', redirectTo: 'error/404' }   //This route has to go last because if it doesn't find any similar to the previous ones

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}