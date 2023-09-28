import { Component, OnDestroy, OnInit } from '@angular/core';
import {Driver} from '../../../interfaces/driver';
import {ActivatedRoute} from '@angular/router';
import {DriversService} from '../../../services/drivers.service';

@Component({
  selector: 'app-car',
  templateUrl: './driver.component.html',
  styleUrls: ['./driver.component.scss']
})
export class DriverComponent implements OnInit, OnDestroy {

  public id: number | undefined;
  private sub: any;
  public driver: Driver = {
    number: 0,
    name: '',
    age: 0,
    teamName: '',
    firstName: '',
    lastName: ''
  };

  constructor(
    private route: ActivatedRoute,
    private driversService: DriversService
  ) { }

  public ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params.id;
      console.log(this.id);
      if (this.id) {
        this.getDriver();
      }
    });
  }

  public getDriver(): void {
    this.driversService.getDriverById(this.id).subscribe((result: Driver) => {
      console.log(result);
      this.driver = result;
    });
  }

  public ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}

