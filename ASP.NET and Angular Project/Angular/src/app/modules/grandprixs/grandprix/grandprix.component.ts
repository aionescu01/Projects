import { Component, OnDestroy, OnInit } from '@angular/core';
import {Grandprix} from '../../../interfaces/grandprix';
import {ActivatedRoute} from '@angular/router';
import {GrandprixsService} from '../../../services/grandprixs.service';

@Component({
  selector: 'app-grandprix',
  templateUrl: './grandprix.component.html',
  styleUrls: ['./grandprix.component.scss']
})
export class GrandprixComponent implements OnInit, OnDestroy {

  public id: number | undefined;
  private sub: any;
  public grandprix: Grandprix = {
    id: 0,
    name: '',
    location: '',
    country: '',
    date: new Date('2021-12-16T10:43:46.737Z'),
    laps: 0
  };

  constructor(
    private route: ActivatedRoute,
    private grandprixsService: GrandprixsService
  ) { }

  public ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params.id;
      console.log(this.id);
      if (this.id) {
        this.getGrandprix();
      }
    });
  }

  public getGrandprix(): void {
    this.grandprixsService.getGrandprixById(this.id).subscribe((result: Grandprix) => {
      console.log(result);
      this.grandprix = result;
    });
  }

  public ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
