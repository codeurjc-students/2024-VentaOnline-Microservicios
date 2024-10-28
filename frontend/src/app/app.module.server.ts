import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { ServerModule } from '@angular/platform-server';

import { AppRoutingModule } from './app.routes';

@NgModule({
  declarations: [
  ],
  imports: [
    BrowserModule,
    ServerModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: []
})
export class AppServerModule { }