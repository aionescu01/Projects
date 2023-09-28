import { Component, OnDestroy, OnInit } from '@angular/core';
import {Constructorsponsor} from '../../../interfaces/constructorsponsor';
import {ActivatedRoute} from '@angular/router';
import {ConstructorsponsorsService} from '../../../services/constructorsponsors.service';

@Component({
  selector: 'app-constructorsponsor',
  templateUrl: './constructorsponsor.component.html',
  styleUrls: ['./constructorsponsor.component.scss']
})
export class ConstructorsponsorComponent implements OnInit, OnDestroy {

  public id: number | undefined;
  private sub: any;
  public constructorsponsor: Constructorsponsor = {
    sponsorId: 0,
    constructorName: '',
    sponsorName: ''
  };

  constructor(
    private route: ActivatedRoute,
    private constructorsponsorsService: ConstructorsponsorsService
  ) { }

  public ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params.id;
      console.log(this.id);
      if (this.id) {
        this.getConstructorsponsor();
      }
    });
  }

  public getConstructorsponsor(): void {
    this.constructorsponsorsService.getConstructorsponsorById(this.id).subscribe((result: Constructorsponsor) => {
      console.log(result);
      this.constructorsponsor = result;
    });
  }

  public ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
