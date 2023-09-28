import {Component, Inject, OnInit} from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CarsService } from '../../../services/cars.service';

@Component({
  selector: 'app-dialog-add-edit-car',
  templateUrl: './dialog-add-edit-car.component.html',
  styleUrls: ['./dialog-add-edit-car.component.scss']
})
export class DialogAddEditCarComponent implements OnInit {

  public title: string;
  public isEditable: boolean;
  public carsForm: FormGroup = new FormGroup({
    number: new FormControl(),
    engineNr: new FormControl(),
    gearboxNr: new FormControl(),
    driverNumber: new FormControl(),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<DialogAddEditCarComponent>,
    private carsService: CarsService,
  ) {
    console.log(this.data);
    if (this.data.car) {
      this.title = 'Edit Car';
      this.carsForm.patchValue(this.data.car);
      this.isEditable = true;
    } else {
      this.title = 'Add Car';
      this.isEditable = false;
    }
  }

  get number(): AbstractControl {
    return this.carsForm.get('number');
  }
  get engineNr(): AbstractControl {
    return this.carsForm.get('engineNr');
  }
  get gearboxNr(): AbstractControl {
    return this.carsForm.get('gearboxNr');
  }
  get driverNumber(): AbstractControl {
    return this.carsForm.get('driverNumber');
  }

  ngOnInit() {
  }

  public closeDialog(): void {
    this.dialogRef.close();
  }

  public saveData(): void {
    console.log(this.carsForm.value);
    if (this.isEditable === false) {
    this.carsService.createCar(this.carsForm.value).subscribe(() => {
      this.dialogRef.close(this.carsForm.value);
    });
    } else {
      this.carsService.editCar(this.carsForm.value).subscribe(() => {
        this.dialogRef.close(this.carsForm.value);
      });
    }
  }

}
