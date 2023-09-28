import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ConstructorsponsorsComponent} from './constructorsponsors/constructorsponsors.component';
import { ConstructorsponsorComponent } from './constructorsponsor/constructorsponsor.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'constructorsponsors',
    pathMatch: 'full'
  },

  {
    path: 'constructorsponsors',
    component: ConstructorsponsorsComponent
  },

  {
    path: 'constructorsponsor/:id',
    component: ConstructorsponsorComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class ConstructorsponsorsRoutingModule { }
