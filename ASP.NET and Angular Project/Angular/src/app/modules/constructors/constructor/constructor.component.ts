import { Component, OnDestroy, OnInit } from '@angular/core';
import {Constructor} from '../../../interfaces/constructor';
import {ActivatedRoute} from '@angular/router';
import {ConstructorsService} from '../../../services/constructors.service';

@Component({
  selector: 'app-constructor',
  templateUrl: './constructor.component.html',
  styleUrls: ['./constructor.component.scss']
})
export class ConstructorComponent implements OnInit, OnDestroy {

  public id: string | undefined;
  private sub: any;
  public constructor1: Constructor = {
    name: '',
    country: '',
    baseLocation: ''
  };

  constructor(
    private route: ActivatedRoute,
    private constructorsService: ConstructorsService
  ) { }

  public ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = params.id;
      console.log(this.id);
      if (this.id) {
        this.getConstructor();
      }
    });
  }

  public getConstructor(): void {
    this.constructorsService.getConstructorById(this.id).subscribe((result: Constructor) => {
      console.log(result);
      this.constructor1 = result;
    });
  }

  public ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
