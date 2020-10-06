import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AppPlcComponent} from "./app-plc/app-plc.component";
import {AppDataComponent} from "./app-data/app-data.component";

const routes: Routes = [
  {path: 'app-plc', component:AppPlcComponent},
  {path: 'app-data', component:AppDataComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
