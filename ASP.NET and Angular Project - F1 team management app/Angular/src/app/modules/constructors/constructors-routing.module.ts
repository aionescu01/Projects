import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ConstructorsComponent} from './constructors/constructors.component';
import { ConstructorComponent } from './constructor/constructor.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'constructors',
    pathMatch: 'full'
  },

  {
    path: 'constructors',
    component: ConstructorsComponent
  },

  {
    path: 'constructor/:id',
    component: ConstructorComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class ConstructorsRoutingModule { }
