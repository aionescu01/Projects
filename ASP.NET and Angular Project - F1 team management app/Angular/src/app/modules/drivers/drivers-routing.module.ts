import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DriversComponent} from './drivers/drivers.component';
import { DriverComponent } from './driver/driver.component';
import {CarComponent} from '../cars/car/car.component';

const routes: Routes = [
  {
    path: 'driver',
    redirectTo: 'drivers',
    pathMatch: 'full'
  },

  {
    path: 'drivers',
    component: DriversComponent
  },

  {
    path: 'driver/:id',
    component: DriverComponent
  },
  {
    path: 'driver/car/:id',
    component: CarComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class DriversRoutingModule { }
