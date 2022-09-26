import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './modules/login/login.component';

const routes: Routes = [
  // adaugat path la homemodule care trb facut
  {
    path: '',
    loadChildren: () => import('src/app/Home/home.module').then(m => m.HomeModule)
  },
  {path: 'login', component: LoginComponent},
  {
    path: '',
    loadChildren: () => import('src/app/modules/cars/cars.module').then(m => m.CarsModule)
  },
  {
    path: '',
    loadChildren: () => import('src/app/modules/drivers/drivers.module').then(m => m.DriversModule)
  },
  {
    path: '',
    loadChildren: () => import('src/app/modules/constructors/constructors.module').then(m => m.ConstructorsModule)
  },
  {
    path: '',
    loadChildren: () => import('src/app/modules/grandprixs/grandprixs.module').then(m => m.GrandprixsModule)
  },
  {
    path: '',
    loadChildren: () => import('src/app/modules/sponsors/sponsors.module').then(m => m.SponsorsModule)
  },
  {
    path: '',
    loadChildren: () => import('src/app/modules/constructorsponsors/constructorsponsors.module').then(m => m.ConstructorsponsorsModule)
  },
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
