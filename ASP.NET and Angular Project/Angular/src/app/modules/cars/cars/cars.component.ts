import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Car } from '../../../interfaces/car';
import { CarsService } from '../../../services/cars.service';
import { CommonModule } from '@angular/common';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {DialogAddEditCarComponent} from '../../shared/dialog-add-edit-car/dialog-add-edit-car.component';

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.scss']
})
export class CarsComponent implements OnInit {

  public cars: Car[] = [];
   public displayedColumns: string[] = ['number', 'driverName', 'constructorName', 'engineNr', 'gearboxNr',
     'profile', 'edit', 'delete'];
  constructor(
    private carsService: CarsService,
    private router: Router,
    private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.getCars();
  }

  public getCars(): void {
    this.carsService.getCars().subscribe((result: Car[]) => {
      console.log(result);
      this.cars = result;
    });
  }

  public addData(car?): void {

    const data = {
      car
    };

    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '550px';
    dialogConfig.height = '700px';
    dialogConfig.data = data;
    const dialogRef = this.dialog.open(DialogAddEditCarComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getCars();
      }
    });

  }

  public deleteCar(car: any): void {
    this.carsService.deleteCar(car).subscribe((result: Car[]) => {
      this.cars = result;
      location.reload();
    });
  }



  public goToCarProfile(id): void {
    this.router.navigate(['/car', id]);
  }

}
