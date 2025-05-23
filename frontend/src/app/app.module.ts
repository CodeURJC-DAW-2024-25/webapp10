import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { ConcertsComponent } from './components/concerts/concerts.component';
import { EditUserComponent } from './components/user/edituser.component';
import { ErrorComponent } from './components/error/error.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { ConcertFormComponent } from './components/concerts/concert-form.component';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserPageComponent } from './components/user/user-page.component';
import { RouterModule } from '@angular/router';
import { ArtistFormComponent } from './components/artists/artist-form.component';
import { PurchaseComponent } from './components/concerts/purchase.component';
import { ConcertInfoComponent } from './components/concerts/concert-info.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ConcertsComponent,
    EditUserComponent,
    ErrorComponent,
    RegisterComponent,
    LoginComponent,
    UserPageComponent,
    ConcertFormComponent,
    ArtistFormComponent,
    PurchaseComponent,
    ConcertInfoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    HttpClientModule,
    NgbModule,
    FormsModule,
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
