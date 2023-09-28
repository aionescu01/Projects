import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {CarsComponent} from './cars/cars.component';
import {CarsRoutingModule} from './cars-routing.module';
import {MatButtonModule, MatCardModule, MatDialogModule, MatIconModule, MatTableModule} from '@angular/material';
import {CarComponent} from './car/car.component';


@NgModule({
  declarations: [CarsComponent, CarComponent],
  imports: [
    CommonModule,
    CarsRoutingModule,
    MatTableModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
    MatDialogModule
  ]
})
export class CarsModule { }
