import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Constructorsponsor } from '../../../interfaces/constructorsponsor';
import { ConstructorsponsorsService } from '../../../services/constructorsponsors.service';
import { CommonModule } from '@angular/common';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import { DialogAddEditConstructorsponsorComponent }
from
'../../shared/dialog-add-edit-constructorsponsor/dialog-add-edit-constructorsponsor.component';

@Component({
  selector: 'app-constructorsponsors',
  templateUrl: './constructorsponsors.component.html',
  styleUrls: ['./constructorsponsors.component.scss']
})
export class ConstructorsponsorsComponent implements OnInit {

  public constructorsponsors: Constructorsponsor[] = [];
  public displayedColumns: string[] = [ 'constructorName', 'sponsorName',
    'profile1', 'profile2', 'delete'];
  constructor(
    private constructorsponsorsService: ConstructorsponsorsService,
    private router: Router,
    private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.getConstructorsponsors();
  }

  public getConstructorsponsors(): void {
    this.constructorsponsorsService.getConstructorsponsors().subscribe((result: Constructorsponsor[]) => {
      console.log(result);
      this.constructorsponsors = result;
    });
  }

  public addData(constructorsponsor?): void {

    const data = {
      constructorsponsor
    };

    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '550px';
    dialogConfig.height = '700px';
    dialogConfig.data = data;
    const dialogRef = this.dialog.open(DialogAddEditConstructorsponsorComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getConstructorsponsors();
      }
    });

  }

  public deleteConstructorsponsor(constructorsponsor: any): void {
    this.constructorsponsorsService.deleteConstructorsponsor(constructorsponsor).subscribe((result: Constructorsponsor[]) => {
      this.constructorsponsors = result;
      location.reload();
    });
  }



  public goToConstructorProfile(id): void {
    this.router.navigate(['/constructor', id]);
  }
  public goToSponsorProfile(name): void {
    this.router.navigate(['/sponsor', name]);
  }

}
