import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {GrandprixsComponent} from './grandprixs/grandprixs.component';
import { GrandprixComponent } from './grandprix/grandprix.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'grandprixs',
    pathMatch: 'full'
  },

  {
    path: 'grandprixs',
    component: GrandprixsComponent
  },

  {
    path: 'grandprixs/:id',
    component: GrandprixComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class GrandprixsRoutingModule { }
