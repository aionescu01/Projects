import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Grandprix } from '../../../interfaces/grandprix';
import { GrandprixsService } from '../../../services/grandprixs.service';
import { CommonModule } from '@angular/common';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {DialogAddEditGrandprixComponent} from '../../shared/dialog-add-edit-grandprix/dialog-add-edit-grandprix.component';

@Component({
  selector: 'app-grandprixs',
  templateUrl: './grandprixs.component.html',
  styleUrls: ['./grandprixs.component.scss']
})
export class GrandprixsComponent implements OnInit {

  public grandprixs: Grandprix[] = [];
  public displayedColumns: string[] = ['id', 'name', 'location', 'country', 'date', 'laps',
    'profile', 'edit', 'delete'];
  constructor(
    private grandprixsService: GrandprixsService,
    private router: Router,
    private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.getGrandprixs();
  }

  public getGrandprixs(): void {
    this.grandprixsService.getGrandprixs().subscribe((result: Grandprix[]) => {
      console.log(result);
      this.grandprixs = result;
    });
  }

  public addData(grandprix?): void {

    const data = {
      grandprix
    };

    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '550px';
    dialogConfig.height = '700px';
    dialogConfig.data = data;
    const dialogRef = this.dialog.open(DialogAddEditGrandprixComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getGrandprixs();
      }
    });

  }

  public deleteGrandprix(grandprix: any): void {
    this.grandprixsService.deleteGrandprix(grandprix).subscribe((result: Grandprix[]) => {
      this.grandprixs = result;
      location.reload();
    });
  }



  public goToGrandprixProfile(id): void {
    this.router.navigate(['/grandprixs', id]);
  }

}
