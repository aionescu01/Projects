import { Component, OnDestroy, OnInit } from '@angular/core';
import {Sponsor} from '../../../interfaces/sponsor';
import {ActivatedRoute} from '@angular/router';
import {SponsorsService} from '../../../services/sponsors.service';

@Component({
  selector: 'app-sponsor',
  templateUrl: './sponsor.component.html',
  styleUrls: ['./sponsor.component.scss']
})
export class SponsorComponent implements OnInit, OnDestroy {

  public id: number | undefined;
  private sub: any;
  public sponsor: Sponsor = {
    id: 0,
    name: ''
  };

  constructor(
    private route: ActivatedRoute,
    private sponsorsService: SponsorsService
  ) { }

  public ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.id = +params.id;
      console.log(this.id);
      if (this.id) {
        this.getSponsor();
      }
    });
  }

  public getSponsor(): void {
    this.sponsorsService.getSponsorById(this.id).subscribe((result: Sponsor) => {
      console.log(result);
      this.sponsor = result;
    });
  }

  public ngOnDestroy(): void {
    this.sub.unsubscribe();
  }
}
