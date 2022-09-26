import { Component, OnDestroy, OnInit } from '@angular/core';
import {Car} from '../../../interfaces/car';
import {ActivatedRoute} from '@angular/router';
import {CarsService} from '../../../services/cars.service';

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.scss']
})
export class CarComponent implements OnInit, OnDestroy {

  public id: number | undefined;
  private sub: any;
  public car: Car = {
    number: 0,
    engineNr: 0,
    gearboxNr: 0,
    driverNumber: 0,
    constructorName: '',
    driverName: ''
  };

  constructor(
    private route: ActivatedRoute,
    private carsService: CarsService
  ) { }

  public ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params.id;
      console.log(this.id);
      if (this.id) {
        this.getCar();
      }
    });
  }

  public getCar(): void {
    this.carsService.getCarById(this.id).subscribe((result: Car) => {
      console.log(result);
      this.car = result;
    });
  }

  public ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
