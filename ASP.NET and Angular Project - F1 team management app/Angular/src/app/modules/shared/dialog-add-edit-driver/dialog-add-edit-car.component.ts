import {Component, Inject, OnInit} from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DriversService } from '../../../services/drivers.service';

@Component({
  selector: 'app-dialog-add-edit-car',
  templateUrl: './dialog-add-edit-car.component.html',
  styleUrls: ['./dialog-add-edit-car.component.scss']
})
export class DialogAddEditDriverComponent implements OnInit {

  public title: string;
  public isEditable: boolean;
  public driversForm: FormGroup = new FormGroup({
    number: new FormControl(),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    age: new FormControl(),
    teamName: new FormControl('')
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<DialogAddEditDriverComponent>,
    private driversService: DriversService,
  ) {
    console.log(this.data);
    if (this.data.driver) {
      this.title = 'Edit Driver';
      this.driversForm.patchValue(this.data.driver);
      this.isEditable = true;
    } else {
      this.title = 'Add Driver';
      this.isEditable = false;
    }
  }

  get number(): AbstractControl {
    return this.driversForm.get('number');
  }

  get firstName(): AbstractControl {
    return this.driversForm.get('firstName');
  }
  get lastName(): AbstractControl {
    return this.driversForm.get('lastName');
  }

  get name(): AbstractControl {
    return this.driversForm.get('name');
  }
  get age(): AbstractControl {
    return this.driversForm.get('age');
  }
  get teamName(): AbstractControl {
    return this.driversForm.get('teamName');
  }

  ngOnInit() {
  }

  public closeDialog(): void {
    this.dialogRef.close();
  }

  public saveData(): void {
    console.log(this.driversForm.value);
    if (this.isEditable === false) {
      this.driversService.createDriver(this.driversForm.value).subscribe(() => {
        this.dialogRef.close(this.driversForm.value);
      });
    } else {
      this.driversService.editDriver(this.driversForm.value).subscribe(() => {
        this.dialogRef.close(this.driversForm.value);
      });
    }
  }

}
