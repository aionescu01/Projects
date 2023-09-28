import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Constructor } from '../../../interfaces/constructor';
import { ConstructorsService } from '../../../services/constructors.service';
import { CommonModule } from '@angular/common';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {DialogAddEditConstructorComponent} from '../../shared/dialog-add-edit-constructor/dialog-add-edit-constructor.component';

@Component({
  selector: 'app-constructors',
  templateUrl: './constructors.component.html',
  styleUrls: ['./constructors.component.scss']
})
export class ConstructorsComponent implements OnInit {

  public constructors: Constructor[] = [];
  public displayedColumns: string[] = ['name', 'country', 'baseLocation',
    'profile', 'edit', 'delete'];
  constructor(
    private constructorsService: ConstructorsService,
    private router: Router,
    private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.getConstructors();
  }

  public getConstructors(): void {
    this.constructorsService.getConstructors().subscribe((result: Constructor[]) => {
      console.log(result);
      this.constructors = result;
    });
  }

  public addData(constructor?): void {

    const data = {
      constructor
    };

    const dialogConfig = new MatDialogConfig();
    dialogConfig.width = '550px';
    dialogConfig.height = '700px';
    dialogConfig.data = data;
    const dialogRef = this.dialog.open(DialogAddEditConstructorComponent, dialogConfig);
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getConstructors();
      }
    });

  }

  public deleteConstructor(constructor: any): void {
    this.constructorsService.deleteConstructor(constructor).subscribe((result: Constructor[]) => {
      this.constructors = result;
      location.reload();
    });
  }



  public goToConstructorProfile(Name): void {
    this.router.navigate(['/constructor', Name]);
  }

}
