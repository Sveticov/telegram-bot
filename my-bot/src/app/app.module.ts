import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppHomeComponent } from './app-home/app-home.component';
import { AppDataComponent } from './app-data/app-data.component';
import { AppPlcComponent } from './app-plc/app-plc.component';

@NgModule({
  declarations: [
    AppComponent,
    AppHomeComponent,
    AppDataComponent,
    AppPlcComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
