import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ConcertsComponent } from './components/concerts/concerts.component';

const appRoutes: Routes = [
  { path: 'concerts', component: ConcertsComponent },
  { path: '', redirectTo: 'concerts', pathMatch: 'full' },
  { path: '**', redirectTo: 'concerts' }
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}