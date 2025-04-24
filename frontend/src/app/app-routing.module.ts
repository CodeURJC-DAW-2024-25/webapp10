import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ConcertsComponent } from './components/concerts/concerts.component';
import { EditUserComponent } from './components/user/edituser.component';
import { ErrorComponent } from './components/error/error.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { UserPageComponent } from './components/user/user-page.component';
import { ConcertFormComponent } from './components/concerts/concert-form.component';
import { ArtistFormComponent } from './components/artists/artist-form.component';

const appRoutes: Routes = [
  { path: '', component: ConcertsComponent },
  { path: 'new-concert', component: ConcertFormComponent },
  { path: 'edit-concert/:id', component: ConcertFormComponent },
  { path: 'edit-artist/:id', component: ArtistFormComponent },
  { path: 'new-artist', component: ArtistFormComponent },
  { path: 'edit-user/:id', component: EditUserComponent},
  { path: 'user-page/:id', component: UserPageComponent},
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