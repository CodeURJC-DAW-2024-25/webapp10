import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ConcertsComponent } from './components/concerts/concerts.component';
import { EditUserComponent } from './components/user/edituser.component';

const appRoutes: Routes = [
  { path: '', component: ConcertsComponent },
  { path: 'edit-user/:id', component: EditUserComponent},
  { path: '', redirectTo: '/', pathMatch: 'full' },
  { path: '**', redirectTo: 'concerts' }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}