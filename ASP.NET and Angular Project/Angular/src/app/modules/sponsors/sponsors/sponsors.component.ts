import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Sponsor } from '../../../interfaces/sponsor';
import { SponsorsService } from '../../../services/sponsors.service';
import { CommonModule } from '@angular/common';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {DialogAddEditSponsorComponent} from '../../shared/dialog-add-edit-sponsor/dialog-add-edit-sponsor.component';

@Component({
  selector: 'app-sponsors',
  templateUrl: './sponsors.component.html',
  styleUrls: ['./sponsors.component.scss']
})
export class SponsorsComponent implements OnInit {

  public sponsors: Sponsor[] = [];
  public displayedColumns: string[] = ['id', 'name',
    'profile', 'edit', 'delete'];
  constructor(
    private sponsorsService: SponsorsService,
    private router: Router,
    private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.getSponsors();
  }

  public getSponsors(): void {
    this.sponsorsService.getSponsors().subscribe((result: Sponsor[]) => {
      console.log(result);
      this.sponsors = result;
    });
  }

  public addData(sponsor?): void {

    const data = {
      sponsor
    };

    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '550px';
    dialogConfig.height = '700px';
    dialogConfig.data = data;
    const dialogRef = this.dialog.open(DialogAddEditSponsorComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getSponsors();
      }
    });

  }

  public deleteSponsor(sponsor: any): void {
    this.sponsorsService.deleteSponsor(sponsor).subscribe((result: Sponsor[]) => {
      this.sponsors = result;
      location.reload();
    });
  }



  public goToSponsorProfile(id): void {
    this.router.navigate(['/sponsor', id]);
  }

}
