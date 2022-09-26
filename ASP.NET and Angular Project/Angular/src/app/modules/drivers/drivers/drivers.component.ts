import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Driver } from '../../../interfaces/driver';
import { DriversService } from '../../../services/drivers.service';
import { CommonModule } from '@angular/common';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {DialogAddEditDriverComponent} from '../../shared/dialog-add-edit-driver/dialog-add-edit-car.component';
import {CarsComponent} from '../../cars/cars/cars.component';

@Component({
  selector: 'app-drivers',
  templateUrl: './drivers.component.html',
  styleUrls: ['./drivers.component.scss']
})
export class DriversComponent implements OnInit {

  public drivers: Driver[] = [];
  public displayedColumns: string[] = ['number', 'name', 'age', 'teamName',
    'profile', 'car', 'edit', 'delete'];
  constructor(
    private driversService: DriversService,
    private router: Router,
    private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.getDrivers();
  }

  public getDrivers(): void {
    this.driversService.getDrivers().subscribe((result: Driver[]) => {
      console.log(result);
      this.drivers = result;
    });
  }

  public addData(driver?): void {

    const data = {
      driver
    };

    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '550px';
    dialogConfig.height = '700px';
    dialogConfig.data = data;
    const dialogRef = this.dialog.open(DialogAddEditDriverComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getDrivers();
      }
    });

  }

  public deleteDriver(driver: any): void {
    this.driversService.deleteDriver(driver).subscribe((result: Driver[]) => {
      this.drivers = result;
      location.reload();
    });
  }



  public goToDriverProfile(id): void {
    this.router.navigate(['/driver', id]);
  }
  public goToCarProfile(id): void {
    this.router.navigate(['/car', id]);
  }

}
