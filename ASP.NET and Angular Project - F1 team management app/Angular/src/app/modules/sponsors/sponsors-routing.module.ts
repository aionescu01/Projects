import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SponsorsComponent} from './sponsors/sponsors.component';
import { SponsorComponent } from './sponsor/sponsor.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'sponsors',
    pathMatch: 'full'
  },

  {
    path: 'sponsors',
    component: SponsorsComponent
  },

  {
    path: 'sponsor/:id',
    component: SponsorComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class SponsorsRoutingModule { }
