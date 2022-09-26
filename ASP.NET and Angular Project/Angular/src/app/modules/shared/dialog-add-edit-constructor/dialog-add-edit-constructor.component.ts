import {Component, Inject, OnInit} from '@angular/core';
import { AbstractControl, FormControl, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ConstructorsService } from '../../../services/constructors.service';

@Component({
  selector: 'app-dialog-add-edit-constructor',
  templateUrl: './dialog-add-edit-constructor.component.html',
  styleUrls: ['./dialog-add-edit-constructor.component.scss']
})
export class DialogAddEditConstructorComponent implements OnInit {

  public title: string;
  public isEditable: boolean;
  public constructorsForm: FormGroup = new FormGroup({
    name: new FormControl(''),
    country: new FormControl(''),
    baseLocation: new FormControl(''),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) public data,
    public dialogRef: MatDialogRef<DialogAddEditConstructorComponent>,
    private constructorsService: ConstructorsService,
  ) {
    console.log(this.data);
    if (this.data.constructor) {
      this.title = 'Edit Constructor';
      this.constructorsForm.patchValue(this.data.constructor);
      this.isEditable = true;
    } else {
      this.title = 'Add Constructor';
      this.isEditable = false;
    }
  }

  get name(): AbstractControl {
    return this.constructorsForm.get('name');
  }
  get country(): AbstractControl {
    return this.constructorsForm.get('country');
  }
  get baseLocation(): AbstractControl {
    return this.constructorsForm.get('baseLocation');
  }


  ngOnInit() {
  }

  public closeDialog(): void {
    this.dialogRef.close();
  }

  public saveData(): void {
    console.log(this.constructorsForm.value);
    if (this.isEditable === false) {
      this.constructorsService.createConstructor(this.constructorsForm.value).subscribe(() => {
        this.dialogRef.close(this.constructorsForm.value);
      });
    } else {
      this.constructorsService.editConstructor(this.constructorsForm.value).subscribe(() => {
        this.dialogRef.close(this.constructorsForm.value);
      });
    }
  }

}
