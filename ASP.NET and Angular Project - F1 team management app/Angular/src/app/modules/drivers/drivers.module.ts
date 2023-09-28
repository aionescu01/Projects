import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DriversComponent} from './drivers/drivers.component';
import {DriversRoutingModule} from './drivers-routing.module';
import {MatButtonModule, MatCardModule, MatDialogModule, MatIconModule, MatTableModule} from '@angular/material';
import {DriverComponent} from './driver/driver.component';
import {CarsModule} from '../cars/cars.module';


@NgModule({
  declarations: [DriversComponent, DriverComponent],
  imports: [
    CommonModule,
    DriversRoutingModule,
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatDialogModule,
    CarsModule,
  ]
})
export class DriversModule { }
