import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { ServerModule } from '@angular/platform-server';

import { AppRoutingModule } from './app.routes';
import { SignupComponent } from './components/signup/signup.component';
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
  ],
  imports: [
    FormsModule,
    BrowserModule,
    ServerModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: []
})
export class AppServerModule { }